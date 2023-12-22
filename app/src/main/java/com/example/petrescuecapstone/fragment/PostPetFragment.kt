package com.example.petrescuecapstone.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.adapter.MyPetAdapter
import com.example.petrescuecapstone.data.UserPreference
import com.example.petrescuecapstone.data.dataStore
import com.example.petrescuecapstone.databinding.FragmentPetBinding
import com.example.petrescuecapstone.response.MypetResponse
import com.example.petrescuecapstone.ui.ChooseReportActivity
import com.example.petrescuecapstone.ui.DetailPetUser

import com.example.petrescuecapstone.viewmodel.MypetViewModel
import com.example.petrescuecapstone.viewmodel.PredictViewModel
import com.example.petrescuecapstone.viewmodel.ViewModelFactory
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import splitties.fragments.start
import splitties.toast.toast

class PostPetFragment : Fragment() {
    private val viewModel by viewModels<MypetViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    lateinit var binding: FragmentPetBinding
    private lateinit var myPetAdapter: MyPetAdapter
    private lateinit var skeleton: Skeleton
    private lateinit var mypetViewModel: MypetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet, container, false)
        binding.lifecycleOwner = this

        binding.addPet.setOnClickListener {
            start<ChooseReportActivity> { }
        }

        return binding.root
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
        binding.rvPet.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPet.adapter = myPetAdapter

        myPetAdapter.setOnItemClickCallBack(object : MyPetAdapter.OnItemClickCallback {

            override fun onItemClicked(user: MypetResponse) {
                val gson = Gson()
                val noteJson = gson.toJson(user)
                val i = Intent(
                    activity!!.baseContext,
                    DetailPetUser::class.java
                )

                i.putExtra("mypetmodel", noteJson)
                startActivity(i)

            }
        })
        val token = "Bearer ${user.token}"
        mypetViewModel.setMypet(token, user.userId.toInt())


        skeleton = binding.rvPet.applySkeleton(R.layout.item_row_pet)
        skeleton.showSkeleton()
    }

    private fun observeViewModel() {
        mypetViewModel.getMyPet().observe(viewLifecycleOwner) { response ->
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