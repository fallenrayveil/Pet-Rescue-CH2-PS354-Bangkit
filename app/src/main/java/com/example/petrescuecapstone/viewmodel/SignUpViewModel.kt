package com.example.petrescuecapstone.viewmodel

import androidx.lifecycle.ViewModel
import com.example.petrescuecapstone.repository.UserRepository

class SignUpViewModel (private val repository: UserRepository): ViewModel() {
    fun signup(name: String, email: String, password: String) =
        repository.userRegistrasi(name, email, password)
}