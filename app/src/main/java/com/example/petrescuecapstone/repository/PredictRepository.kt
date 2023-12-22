package com.example.petrescuecapstone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.data.UserPreference
import com.example.petrescuecapstone.network.ApiConfig
import com.example.petrescuecapstone.network.ApiService
import com.example.petrescuecapstone.response.ErrorResponse
import com.example.petrescuecapstone.response.PredictResponse
import com.example.petrescuecapstone.response.SignInResponse
import com.example.petrescuecapstone.response.SignUpResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.HttpException

class PredictRepository private constructor (
    private val userPreference: UserPreference,
    private val apiService: ApiService
){
    fun postPredict(image:MultipartBody.Part):LiveData<UiState<PredictResponse>> = liveData{
        emit(UiState.Loading)

        try {
            val response = apiService.predict(image)
            if (response.petId!=null){
                emit(UiState.Success(response))
            }else{
                emit(UiState.Error(response.error ?: "An error occurred"))
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
        private var instance: PredictRepository? = null
        fun getInstance(
            userPreference: UserPreference, apiService: ApiService
        ): PredictRepository =
            instance ?: synchronized(this) {
                instance ?: PredictRepository(userPreference, apiService)
            }.also { instance = it }
    }

}