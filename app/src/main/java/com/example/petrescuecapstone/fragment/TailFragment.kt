package com.example.petrescuecapstone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.adapter.PetSwipeAdapter
import com.example.petrescuecapstone.databinding.FragmentTailBinding
import com.example.petrescuecapstone.ui.SettingActivity
import splitties.fragments.start

class TailFragment : Fragment() {

    companion object{
        var viewPager: ViewPager? = null
    }

    var setting : ImageButton? = null
    lateinit var root : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root =  inflater.inflate(R.layout.fragment_tail, container, false)

        viewPager = root.findViewById(R.id.viewpet)
         setting = root.findViewById(R.id.setting)

        setting!!.setOnClickListener {
            start<SettingActivity> {  }
        }
        val fragmentAdapter = PetSwipeAdapter(requireActivity().supportFragmentManager)

        // Inflate the layout for this fragment


        viewPager!!.adapter = fragmentAdapter


        return  root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpViewPager(viewPager!!)

    }

    private fun setUpViewPager(viewPager: ViewPager) {
        val adapter = PetSwipeAdapter(childFragmentManager)
        adapter.addFragment(LostFragment())
        adapter.addFragment(FoundFragment())
        viewPager.adapter = adapter
    }


}