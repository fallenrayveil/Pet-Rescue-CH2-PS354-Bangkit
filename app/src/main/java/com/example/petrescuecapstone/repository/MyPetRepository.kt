package com.example.petrescuecapstone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.data.UserPreference
import com.example.petrescuecapstone.network.ApiService
import com.example.petrescuecapstone.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

class MyPetRepository private constructor (
    private val userPreference: UserPreference,
    private val apiService: ApiService

){


    fun delete_mypet(id:Int):LiveData<UiState<ErrorResponse>> = liveData{
        emit(UiState.Loading)

        try {
            val token = "Bearer ${userPreference.getToken()}"
            val response = apiService.delete_mypet(token,id)
            if (response.error == false){
                emit(UiState.Success(response))
            }else{
                emit(UiState.Error((response.error ?: "An error occurred").toString()))
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

    fun update_status(id:Int):LiveData<UiState<ErrorResponse>> = liveData{
        emit(UiState.Loading)

        try {
            val token = "Bearer ${userPreference.getToken()}"
            val response = apiService.update_status(token,id)
            if (response.error == false){
                emit(UiState.Success(response))
            }else{
                emit(UiState.Error((response.error ?: "An error occurred").toString()))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(UiState.Error("Registration failed: $errorMessage"))
        }catch (e: Exception){
            Log.d("surabaya", "update_status: ${e.message}")
            emit(UiState.Error("Internet Issues"))
        }
    }

    companion object {
        @Volatile
        private var instance: MyPetRepository? = null
        fun getInstance(
            userPreference: UserPreference, apiService: ApiService
        ): MyPetRepository =
            instance ?: synchronized(this) {
                instance ?: MyPetRepository(userPreference, apiService)
            }.also { instance = it }
    }

}