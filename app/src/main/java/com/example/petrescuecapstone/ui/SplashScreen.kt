package com.example.petrescuecapstone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.Session.SessionManager
import splitties.activities.start

class SplashScreen : AppCompatActivity() {
    lateinit var handler : Handler
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sessionManager = SessionManager(this)
        handler = Handler()
        if (sessionManager.getLogin()==true){
            handler = Handler()
            handler.postDelayed({
                start<MainActivity> {  }
                finish()
            }, 3000)
        }else{
            handler = Handler()
            handler.postDelayed({
                start<IntroSlide1> {  }
                finish()
            }, 3000)
        }


    }


}