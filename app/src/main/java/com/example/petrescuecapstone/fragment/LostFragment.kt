package com.example.petrescuecapstone.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.adapter.MyPetAdapter
import com.example.petrescuecapstone.data.UserPreference
import com.example.petrescuecapstone.data.dataStore
import com.example.petrescuecapstone.databinding.FragmentFoundBinding
import com.example.petrescuecapstone.databinding.FragmentLostBinding
import com.example.petrescuecapstone.response.MypetResponse
import com.example.petrescuecapstone.ui.DetailPetOtherUser
import com.example.petrescuecapstone.viewmodel.MypetViewModel
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class LostFragment : Fragment() {
    private lateinit var myPetAdapter: MyPetAdapter
    private lateinit var skeleton: Skeleton
    private lateinit var mypetViewModel: MypetViewModel
    lateinit var binding : FragmentLostBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lost,container,false)
        binding.lifecycleOwner = this

        binding.btnFound.setOnClickListener {
            TailFragment.viewPager!!.currentItem =0
        }
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        initUI()
        observeViewModel()

    }

    private fun initUI() {
        val pref = UserPreference.getInstance(requireContext().dataStore)
        val user = runBlocking {
            pref.getSession().first()
        }

        mypetViewModel = ViewModelProvider(this).get(MypetViewModel::class.java)

        myPetAdapter = MyPetAdapter()
        binding.rvFound.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFound.adapter = myPetAdapter

        myPetAdapter.setOnItemClickCallBack(object : MyPetAdapter.OnItemClickCallback {

            override fun onItemClicked(user: MypetResponse) {
                val gson = Gson()
                val noteJson = gson.toJson(user)
                val i = Intent(
                    activity!!.baseContext,
                    DetailPetOtherUser::class.java
                )

                i.putExtra("mypetmodel", noteJson)
                startActivity(i)

            }
        })
        val token = "Bearer ${user.token}"
        mypetViewModel.setlost(token)


        skeleton = binding.rvFound.applySkeleton(R.layout.item_row_pet)
        skeleton.showSkeleton()
    }

    private fun observeViewModel() {
        mypetViewModel.getLost().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val data = response
                if (data != null) {
                    myPetAdapter.setList(data)
                    skeleton.showOriginal()
                } else {
                    Log.e(this::class.java.simpleName, "Data not found")
                }
            } else {
                Log.e(this::class.java.simpleName, "Response is null")
            }
        }
    }
}