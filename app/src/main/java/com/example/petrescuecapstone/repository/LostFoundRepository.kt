package com.example.petrescuecapstone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.data.UserPreference
import com.example.petrescuecapstone.network.ApiService
import com.example.petrescuecapstone.response.ErrorResponse
import com.example.petrescuecapstone.response.FoundLostResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class LostFoundRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun post_lost_found(
        user_id: Int,
        pet_id: Int,
        pet_name: String,
        gender: String,
        reward: Double,
        province: String,
        regency: String,
        found_area: String,
        email: String,
        phone_number: String,
        detail: String,
        date_lost_found: String
    ): LiveData<UiState<FoundLostResponse>> = liveData {
        emit(UiState.Loading)

        Log.d(
            "surabaya",
            "post_lost_found: $user_id $pet_id $pet_name $gender $reward $province $regency $found_area $email $phone_number" +
                    "$detail $date_lost_found"
        )
        try {
            val token = "Bearer ${userPreference.getToken()}"
            var response: FoundLostResponse? = null
            if (detail == "lost") {
                 response = apiService.postfoundlost(
                    token,
                    user_id,
                    pet_id,
                    pet_name,
                    gender,
                    reward,
                    province,
                    regency,
                    found_area,
                    email,
                    phone_number,
                    detail,
                    date_lost_found
                )

            } else if (detail == "found") {
                 response = apiService.postfound(
                    token,
                    user_id,
                    pet_id,
                    pet_name,
                    gender,
                    reward,
                    province,
                    regency,
                    found_area,
                    email,
                    phone_number,
                    detail,
                    date_lost_found
                )
            }

            if (response!!.error == false) {
                emit(UiState.Success(response))
            } else {
                emit(UiState.Error(response.message ?: "An error occurred"))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(UiState.Error("Registration failed: $errorMessage"))
        } catch (e: Exception) {
            Log.d("surabaya", "post_lost_found: ${e.message}")
            emit(UiState.Error("Internet Issues"))
        }
    }


    companion object {
        @Volatile
        private var instance: LostFoundRepository? = null
        fun getInstance(
            userPreference: UserPreference, apiService: ApiService
        ): LostFoundRepository =
            instance ?: synchronized(this) {
                instance ?: LostFoundRepository(userPreference, apiService)
            }.also { instance = it }
    }

}