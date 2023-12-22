package com.example.petrescuecapstone.data

import android.content.Context
import android.util.Log
import com.example.petrescuecapstone.network.ApiConfig
import com.example.petrescuecapstone.repository.LostFoundRepository
import com.example.petrescuecapstone.repository.MyPetRepository
import com.example.petrescuecapstone.repository.PredictRepository
import com.example.petrescuecapstone.repository.UserRepository
import com.example.petrescuecapstone.viewmodel.MypetViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(preferences, apiService)
    }

    fun predictRepository(context: Context): PredictRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking {
            pref.getSession().first() }
        val apiService = ApiConfig.getApiServicePredict()
        return PredictRepository.getInstance(pref, apiService)

    }

    fun lostfoundRepository(context: Context): LostFoundRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking {
            pref.getSession().first() }
        Log.d("surabaya", "lostfoundRepository: ${user.token}")
        val apiService = ApiConfig.getApiService()
        return LostFoundRepository.getInstance(pref, apiService)

    }

    fun mypetRepository(context: Context): MyPetRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking {
            pref.getSession().first() }
        Log.d("surabaya", "lostfoundRepository: ${user.token}")
        val apiService = ApiConfig.getApiService()
        return MyPetRepository.getInstance(pref, apiService)

    }

}