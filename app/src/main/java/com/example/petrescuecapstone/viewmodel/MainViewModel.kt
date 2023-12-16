package com.example.petrescuecapstone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.repository.UserRepository

class MainViewModel(private val repository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}