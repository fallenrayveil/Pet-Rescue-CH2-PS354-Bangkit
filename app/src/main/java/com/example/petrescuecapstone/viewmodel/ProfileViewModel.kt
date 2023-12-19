package com.example.petrescuecapstone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petrescuecapstone.repository.UserRepository
import com.example.petrescuecapstone.response.ProfileResponse

class ProfileViewModel(private val repository: UserRepository): ViewModel() {


    fun getProfile(): LiveData<ProfileResponse> {
        return repository.getProfile()
    }
    suspend fun logout() {
        repository.logout()
    }


}