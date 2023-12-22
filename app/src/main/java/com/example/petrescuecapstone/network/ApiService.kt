package com.example.petrescuecapstone.network

import com.example.petrescuecapstone.response.ProfileResponse
import com.example.petrescuecapstone.response.ArticleResponse
import com.example.petrescuecapstone.response.ErrorResponse
import com.example.petrescuecapstone.response.FoundLostResponse
import com.example.petrescuecapstone.response.MypetResponse
import com.example.petrescuecapstone.response.PredictResponse
import com.example.petrescuecapstone.response.SignInResponse
import com.example.petrescuecapstone.response.SignUpResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part image: MultipartBody.Part?
    ): PredictResponse


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
        @Header("Authorization") token: String
    ): Call<ProfileResponse>

    @FormUrlEncoded
    @PUT("report/lost")
    suspend fun postfoundlost(
        @Header("Authorization") token: String,
        @Field("user_id") userId: Int,
        @Field("pet_id") petId: Int,
        @Field("pet_name") petName: String,
        @Field("gender") gender: String,
        @Field("reward") reward: Double,
        @Field("province") province: String,
        @Field("regency") regency: String,
        @Field("found_area") foundArea: String,
        @Field("email") email: String,
        @Field("phone_number") phoneNumber: String,
        @Field("detail") detail: String,
        @Field("date_lost_found") dateLostFound: String
    ): FoundLostResponse

    @FormUrlEncoded
    @PUT("report/found")
    suspend fun postfound(
        @Header("Authorization") token: String,
        @Field("user_id") userId: Int,
        @Field("pet_id") petId: Int,
        @Field("pet_name") petName: String,
        @Field("gender") gender: String,
        @Field("reward") reward: Double,
        @Field("province") province: String,
        @Field("regency") regency: String,
        @Field("found_area") foundArea: String,
        @Field("email") email: String,
        @Field("phone_number") phoneNumber: String,
        @Field("detail") detail: String,
        @Field("date_lost_found") dateLostFound: String
    ): FoundLostResponse

    @GET("posts/{id}")
    fun get_mypet(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<List<MypetResponse>>

    @GET("found")
    fun found(
        @Header("Authorization") token: String
    ): Call<List<MypetResponse>>

    @GET("lost")
    fun lost(
        @Header("Authorization") token: String
    ): Call<List<MypetResponse>>

    @GET("search/{query}")
    fun searchpet(
        @Header("Authorization") token: String,
        @Path("query") query: String
    ): Call<List<MypetResponse>>

    @DELETE("pet/{id}")
    suspend fun delete_mypet(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ErrorResponse


    @PUT("pet/status/{id}")
    suspend fun update_status(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ErrorResponse


}



