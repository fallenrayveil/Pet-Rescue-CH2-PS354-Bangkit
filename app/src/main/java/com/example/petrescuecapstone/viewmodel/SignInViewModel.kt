package com.example.petrescuecapstone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.repository.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository): ViewModel() {
    fun login(email: String, password: String)= repository.getLogin(email, password)
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

}