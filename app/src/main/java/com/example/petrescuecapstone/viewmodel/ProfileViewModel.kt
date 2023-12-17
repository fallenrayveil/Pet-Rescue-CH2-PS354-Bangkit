package com.example.petrescuecapstone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petrescuecapstone.response.ProfileResponse

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