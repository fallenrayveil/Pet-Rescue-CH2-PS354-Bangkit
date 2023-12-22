package com.example.petrescuecapstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.Session.SessionManager
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.data.UserPreference
import com.example.petrescuecapstone.data.dataStore
import com.example.petrescuecapstone.databinding.ActivityInfoPetStatus2Binding
import com.example.petrescuecapstone.databinding.ActivityInfoPetStatus3Binding
import com.example.petrescuecapstone.viewmodel.LostFoundViewModel
import com.example.petrescuecapstone.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import splitties.toast.toast

class InfoPetStatus3Activity : AppCompatActivity() {
    lateinit var binding: ActivityInfoPetStatus3Binding
    lateinit var sessionManager: SessionManager
    private val viewModel by viewModels<LostFoundViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info_pet_status3)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)


        val intent = intent
        val pet_id = intent.getIntExtra("pet_id",0)
        val pet_name = intent.getStringExtra("pet_name")
        val gender = intent.getStringExtra("gender")
        val detail = intent.getStringExtra("detail")
        val email = intent.getStringExtra("email")
        val phone_number = intent.getStringExtra("phone_number")
        val tanggal = intent.getStringExtra("tanggal")

        val pref = UserPreference.getInstance(dataStore)
        val user = runBlocking {
            pref.getSession().first() }

        Log.d("surabaya", "onCreate: $detail $pet_id $pet_name $gender  $email $phone_number $tanggal ${user.userId.toInt()}")
        binding.btnUpload.setOnClickListener {
            val province = binding.edtprovince.text.toString().trim()
            val regency = binding.edtregency.text.toString().trim()
            val area = binding.edtarea.text.toString().trim()
            val reward = binding.edtreward.text.toString().trim()

            if (province.isNotEmpty() && regency.isNotEmpty() && area.isNotEmpty()  ) {
                viewModel.post_lost_fond(
                    user.userId.toInt(),
                    pet_id.toInt(),
                    pet_name!!,
                    gender!!,
                    reward.toDouble(),
                    province,
                    regency,
                    area,
                    email!!,
                    phone_number!!,
                    detail!!,
                    tanggal!!
                ).observe(this) { result ->
                    when (result) {
                        is UiState.Loading -> {
                            showLoading(true)
                        }

                        is UiState.Success -> {
                            showToast("Upload berhasil")
                            finish()
                            InfoPetStatus2Activity.view!!.finish()
                            InfoPetStatus.view!!.finish()
                            ChooseReportActivity.view!!.finish()
                        }

                        is UiState.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }

                    }
                }
            }else{
                Snackbar.make(it,"Jangan kosongi kolom",3000).show()
            }
        }


    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        toast(message)
    }

}