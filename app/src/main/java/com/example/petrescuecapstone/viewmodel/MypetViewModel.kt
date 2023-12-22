package com.example.petrescuecapstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.response.ArticleResponse
import com.example.petrescuecapstone.network.ApiConfig
import com.example.petrescuecapstone.network.ApiService
import com.example.petrescuecapstone.response.ErrorResponse
import com.example.petrescuecapstone.response.FoundLostResponse
import com.example.petrescuecapstone.response.MypetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast

class MypetViewModel : ViewModel() {
    val listMyPet = MutableLiveData<List<MypetResponse>>()
    val listFound = MutableLiveData<List<MypetResponse>>()
    val listLost = MutableLiveData<List<MypetResponse>>()
    val listSearch = MutableLiveData<List<MypetResponse>>()

    fun setMypet(token: String, pet_id: Int) {
        ApiConfig.getApiService().get_mypet(token, pet_id)
            .enqueue(object : Callback<List<MypetResponse>> {
                override fun onResponse(
                    call: Call<List<MypetResponse>>,
                    response: Response<List<MypetResponse>>
                ) {
                    listMyPet.postValue(response.body())
                }

                override fun onFailure(call: Call<List<MypetResponse>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })

    }
    fun getMyPet(): LiveData<List<MypetResponse>> {
        return listMyPet
    }

    fun setfound(token: String) {
        ApiConfig.getApiService().found(token)
            .enqueue(object : Callback<List<MypetResponse>> {
                override fun onResponse(
                    call: Call<List<MypetResponse>>,
                    response: Response<List<MypetResponse>>
                ) {
                    listFound.postValue(response.body())
                }

                override fun onFailure(call: Call<List<MypetResponse>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })

    }
    fun getFound(): LiveData<List<MypetResponse>> {
        return listFound
    }

    fun setlost(token: String) {
        ApiConfig.getApiService().lost(token)
            .enqueue(object : Callback<List<MypetResponse>> {
                override fun onResponse(
                    call: Call<List<MypetResponse>>,
                    response: Response<List<MypetResponse>>
                ) {
                    listLost.postValue(response.body())
                }

                override fun onFailure(call: Call<List<MypetResponse>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })

    }
    fun getLost(): LiveData<List<MypetResponse>> {
        return listLost
    }


    fun setSearch(token: String, query : String) {
        ApiConfig.getApiService().searchpet(token,query)
            .enqueue(object : Callback<List<MypetResponse>> {
                override fun onResponse(
                    call: Call<List<MypetResponse>>,
                    response: Response<List<MypetResponse>>
                ) {
                    listSearch.postValue(response.body())
                }

                override fun onFailure(call: Call<List<MypetResponse>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })

    }
    fun getSearch(): LiveData<List<MypetResponse>> {
        return listSearch
    }




}

