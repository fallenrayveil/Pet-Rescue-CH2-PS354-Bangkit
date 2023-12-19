package com.example.petrescuecapstone.data

import android.content.Context
import com.example.petrescuecapstone.network.ApiConfig
import com.example.petrescuecapstone.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(preferences, apiService)
    }
}