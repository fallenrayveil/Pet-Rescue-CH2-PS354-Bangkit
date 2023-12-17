package com.example.petrescuecapstone.network

import com.example.petrescuecapstone.response.ProfileResponse
import com.example.petrescuecapstone.response.ArticleResponse
import com.example.petrescuecapstone.response.SignInResponse
import com.example.petrescuecapstone.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignUpResponse


    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): SignInResponse

    @GET("news?access_key=be1b8364856fd56c46f6f81a78cdd402&%20keywords=kucing")
    fun getArticle(
    ): Call<ArticleResponse>

    @GET("profile")
    fun getProfile(
        @Header("Authorization") token: String,
    ): ProfileResponse
}



