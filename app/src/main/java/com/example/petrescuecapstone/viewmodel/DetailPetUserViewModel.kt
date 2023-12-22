package com.example.petrescuecapstone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.repository.MyPetRepository
import com.example.petrescuecapstone.repository.PredictRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class DetailPetUserViewModel(private val repository: MyPetRepository): ViewModel() {

    fun delete_mypet(id : Int)= repository.delete_mypet(id)
    fun update_status(id : Int)= repository.update_status(id)

}