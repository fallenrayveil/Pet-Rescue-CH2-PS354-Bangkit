package com.example.petrescuecapstone.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.setting.SettingPreferences
import com.example.petrescuecapstone.setting.SettingViewModel
import com.example.petrescuecapstone.setting.SettingViewModelfactory
import com.example.petrescuecapstone.setting.dataStore
import com.example.petrescuecapstone.databinding.ActivityMainBinding
import com.example.petrescuecapstone.fragment.ArticleFragment
import com.example.petrescuecapstone.fragment.PostPetFragment
import com.example.petrescuecapstone.fragment.ProfileFragment
import com.example.petrescuecapstone.fragment.SearchFragment
import com.example.petrescuecapstone.fragment.TailFragment
import com.example.petrescuecapstone.viewmodel.MainViewModel
import com.example.petrescuecapstone.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView


    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this, SettingViewModelfactory(pref)).get(
            SettingViewModel::class.java)

        viewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val tailFragment = TailFragment()
        val searchFragment = SearchFragment()
        val articleFragment = ArticleFragment()
        val postPetFragment = PostPetFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(tailFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.button_list_pet -> makeCurrentFragment(tailFragment)
                R.id.button_search -> makeCurrentFragment(searchFragment)
                R.id.button_article -> makeCurrentFragment(articleFragment)
                R.id.button_pet-> makeCurrentFragment(postPetFragment)
                R.id.button_account-> makeCurrentFragment(profileFragment)
            }

            true
        }
        // Select the initial menu item programmatically
        bottomNavigation.selectedItemId = R.id.button_list_pet
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, fragment)
            commit()
        }

    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}