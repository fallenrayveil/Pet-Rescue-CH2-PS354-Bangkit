package com.example.petrescuecapstone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petrescuecapstone.response.ArticleResponse
import com.example.petrescuecapstone.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel() {
    val listUser = MutableLiveData<ArticleResponse>()
    fun setArticle() {
        ApiConfig.getApiServiceArticle()
            .getArticle()
            .enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(
                    call: Call<ArticleResponse>,
                    response: Response<ArticleResponse>
                ) {
                    listUser.postValue(response.body())
                }
                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })

    }

    fun getArticle(): LiveData<ArticleResponse> {
        return listUser
    }

}