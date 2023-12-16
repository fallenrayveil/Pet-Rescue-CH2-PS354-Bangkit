package com.example.petrescuecapstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.data.UserPreference
import com.example.petrescuecapstone.network.ApiConfig
import com.example.petrescuecapstone.network.ApiService
import com.example.petrescuecapstone.response.ErrorResponse
import com.example.petrescuecapstone.response.SignInResponse
import com.example.petrescuecapstone.response.SignUpResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor (
    private val userPreference: UserPreference,
    private val apiService: ApiService

){
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getLogin(
        email: String,
        password: String
    ): LiveData<UiState<SignInResponse>> = liveData {
        emit(UiState.Loading)
        try {
            val response = apiService.login(email, password)
            if (response.error == false) {
                val tokenAuth = UserModel(name= response.loginResult.name?: "", userId = response.loginResult.userId?: " ", token = response.loginResult.token?: "", isLogin = true)
                ApiConfig.token= response.loginResult.token
                userPreference.saveSession(tokenAuth)
                emit(UiState.Success(response))
            } else {
                emit(UiState.Error(response.message ?: "An error occurred"))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(UiState.Error("Login error: $errorMessage"))
        }
    }
    fun userRegistrasi(name:String,email:String, password:String):LiveData<UiState<SignUpResponse>> = liveData{
        emit(UiState.Loading)
        try {
            val response = apiService.register(name, email, password)
            if (response.error == false) {
                emit(UiState.Success(response))
            } else {
                emit(UiState.Error(response.message ?: "An error occurred"))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(UiState.Error("Registration failed: $errorMessage"))
        }catch (e: Exception){
            emit(UiState.Error("Internet Issues"))
        }
    }
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference, apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }

}