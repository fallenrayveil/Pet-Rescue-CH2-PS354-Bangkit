package com.example.petrescuecapstone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.repository.PredictRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PredictViewModel(private val repository: PredictRepository): ViewModel() {
    fun predict(image: MultipartBody.Part)= repository.postPredict(image)


}