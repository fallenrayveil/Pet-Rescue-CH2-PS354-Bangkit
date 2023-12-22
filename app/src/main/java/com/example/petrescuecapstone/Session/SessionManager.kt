package com.example.petrescuecapstone.Session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(private val context: Context?) {
    val privateMode = 0
    val privateName ="login"
    var Pref : SharedPreferences?=context?.getSharedPreferences(privateName,privateMode)
    var editor : SharedPreferences.Editor?=Pref?.edit()

    private val islogin = "login"
    fun setLogin(check: Boolean){
        editor?.putBoolean(islogin,check)
        editor?.commit()
    }

    fun getLogin():Boolean?
    {
        return Pref?.getBoolean(islogin,false)
    }

    private val isuser_id  = "user_id"
    fun setuser_id (check: Int){
        editor?.putInt(isuser_id,check)
        editor?.commit()
    }

    fun getuser_id():Int?
    {
        return Pref?.getInt(isuser_id,0)
    }

    private val is_token  = "token"
    fun settoken (check: String){
        editor?.putString(is_token,check)
        editor?.commit()
    }

    fun gettoken():String?
    {
        return Pref?.getString(is_token,"")
    }



}