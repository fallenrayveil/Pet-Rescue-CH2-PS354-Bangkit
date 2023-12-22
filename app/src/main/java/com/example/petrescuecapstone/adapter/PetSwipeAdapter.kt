package com.example.petrescuecapstone.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.petrescuecapstone.fragment.FoundFragment
import com.example.petrescuecapstone.fragment.LostFragment
import java.util.*

class PetSwipeAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    private var titleList = ArrayList<String>()
    private var fragmentList= ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LostFragment()
            }
            1 -> {
                FoundFragment()
            }
            else -> {
                LostFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    fun addFragment(
        fragment: Fragment?
    ) {
        fragmentList.add(fragment!!)

    }

}