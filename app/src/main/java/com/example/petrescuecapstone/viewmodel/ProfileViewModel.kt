package com.example.petrescuecapstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petrescuecapstone.ProfileResponse
import com.example.petrescuecapstone.network.ApiConfig
import com.example.petrescuecapstone.response.ArticleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {
    val listUser = MutableLiveData<ProfileResponse>()
//    fun setProfile(token: String) {
//        ApiConfig.getApiService(token)
//            .getProfile(token)
//            .enqueue(object : Callback<ProfileResponse> {
//                override fun onResponse(
//                    call: Call<ProfileResponse>,
//                    response: Response<ProfileResponse>
//                ) {
//                    listUser.postValue(response.body())
//                }
//                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                    Log.d("Failure", t.message!!)
//                }
//            })
//
//    }

    fun getProfile(): LiveData<ProfileResponse> {
        return listUser
    }


}