package com.example.petrescuecapstone.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.databinding.ActivityInfoPetStatus2Binding
import com.google.android.material.snackbar.Snackbar

class InfoPetStatus2Activity : AppCompatActivity() {
    lateinit var binding : ActivityInfoPetStatus2Binding
    companion object{
        var view: Activity? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_info_pet_status2)
        binding.lifecycleOwner = this

        view = this
        val intent = intent
        val pet_id = intent.getIntExtra("pet_id",0)
        val pet_name = intent.getStringExtra("pet_name")
        val gender = intent.getStringExtra("gender")
        val detail = intent.getStringExtra("detail")
        val tanggal = intent.getStringExtra("tanggal")

        Log.d("surabaya", "onCreate: $tanggal")

        binding.btnUpload.setOnClickListener {
            val email = binding.edtemail.text.toString().trim()
            val phone = binding.edtphonenumber.text.toString().trim()

            if (email.isNotEmpty() &&  phone.isNotEmpty()){
                val intent = Intent(this, InfoPetStatus3Activity::class.java)
                intent.putExtra("email", email )
                intent.putExtra("phone_number", phone )
                intent.putExtra("pet_id", pet_id )
                intent.putExtra("pet_name", pet_name )
                intent.putExtra("gender", gender )
                intent.putExtra("detail", detail )
                intent.putExtra("tanggal", tanggal )
                startActivity(intent)
            }else{
                Snackbar.make(it,"Jangan kosongi kolom",3000).show()
            }
        }
    }
}