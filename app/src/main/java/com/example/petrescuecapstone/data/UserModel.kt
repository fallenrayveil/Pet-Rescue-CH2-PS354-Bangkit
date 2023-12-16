package com.example.petrescuecapstone.data

data class UserModel (
    val name: String,
    val userId: String,
    val token: String,
    val isLogin: Boolean = false
)