package com.example.petrescuecapstone.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.Session.SessionManager
import com.example.petrescuecapstone.databinding.FragmentProfileBinding
import com.example.petrescuecapstone.ui.SignInActivity
import com.example.petrescuecapstone.viewmodel.ProfileViewModel
import com.example.petrescuecapstone.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    //    private val profileViewModel by viewModels<ProfileViewModel> {
//        ViewModelFactory.getInstance(requireContext())
//    }
    private val profileViewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun initUI() {
//        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // Set up logout button and other UI elements
        binding.logoutFab.setOnClickListener {
            // Launch a coroutine in the lifecycleScope
            lifecycleScope.launch {
                // Call logout function from the ViewModel
                sessionManager.setLogin(false)
                profileViewModel.logout()
                // Handle any post-logout logic here, like navigating to the login screen
                startActivity(Intent(requireActivity(), SignInActivity::class.java))
                requireActivity().finish()
            }
        }
    }

//    private fun observeViewModel() {
//        profileViewModel.getProfile().observe(viewLifecycleOwner) { profileResponse ->
//            // Update UI with the profile data
//            // For example:
//            binding.nameTextView.text = profileResponse.name
//            binding.nameEditText.setText(profileResponse.name)
//            binding.emailEditText.setText(profileResponse.email)
//        }
//    }

    private fun observeViewModel() {
        profileViewModel.getProfile().observe(viewLifecycleOwner) { profileResponse ->
            // Update UI with the profile data
            // For example:
            binding.nameTextView.text = profileResponse.name
            binding.nameEditText.setText(profileResponse.name)
            binding.emailEditText.setText(profileResponse.email)
        }
    }
}
