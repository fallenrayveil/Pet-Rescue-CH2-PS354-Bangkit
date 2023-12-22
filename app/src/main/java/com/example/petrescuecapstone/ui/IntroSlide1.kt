package com.example.petrescuecapstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.petrescuecapstone.R

class IntroSlide1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slide1)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, IntroSlide2::class.java)
            startActivity(intent)
        })
    }
}