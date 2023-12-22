package com.example.petrescuecapstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petrescuecapstone.network.ApiConfig
import com.example.petrescuecapstone.repository.LostFoundRepository
import com.example.petrescuecapstone.response.ArticleResponse
import com.example.petrescuecapstone.response.FoundLostResponse
import com.example.petrescuecapstone.response.MypetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LostFoundViewModel(private val repository: LostFoundRepository) : ViewModel() {

    fun post_lost_fond(
        user_id: Int,
        pet_id: Int,
        pet_name: String,
        gender: String,
        reward : Double,
        province : String,
        regency : String,
        found_area : String,
        email : String,
        phone_number : String,
        detail : String,
        date_lost_found : String,
    ) = repository.post_lost_found(user_id,pet_id,pet_name,gender,reward,province,regency,found_area,email,phone_number,detail,date_lost_found)



}