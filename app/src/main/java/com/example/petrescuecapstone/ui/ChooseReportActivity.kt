package com.example.petrescuecapstone.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.databinding.ActivityChooseReportBinding

class ChooseReportActivity : AppCompatActivity() {
    lateinit var binding : ActivityChooseReportBinding

    companion object{
        var view: Activity? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_choose_report)
        binding.lifecycleOwner = this
        view= this
        binding.arrowBack.setOnClickListener {
            finish()
        }

        binding.btnlostpet.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            intent.putExtra(
                "detail","lost"
            )
            startActivity(intent)
        }

        binding.btnfoundpet.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            intent.putExtra(
                "detail","found"
            )
            startActivity(intent)
        }
    }
}