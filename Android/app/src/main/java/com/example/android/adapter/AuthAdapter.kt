package com.example.android.adapter

import android.util.Log
import android.widget.Toast
import com.example.android.reponse.LoginResponse
import com.example.android.reponse.RegisterResponse
import com.example.android.service.ApiService
import com.example.android.service.AuthService
import retrofit2.Call
import java.lang.Exception

object AuthAdapter {

    /**
     * This method allows us to register a new user into the database.
     */
    fun registerUser(name : String, email: String, password : String, password_confirmation: String){

        val requestCall : Call<RegisterResponse> = ApiService.
        buildService(AuthService::class.java).register(name, email, password, password_confirmation)
        try {
            val response = requestCall.execute()
            Log.v("Json", response.body().toString())

        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    /**
     * This method allows us to log in into the app.
     */
    fun loginUser(email : String, password: String) : LoginResponse? {

        val requestCall : Call<LoginResponse> = ApiService.
        buildService(AuthService::class.java).login(email, password)

        try{
            val response  = requestCall.execute()

            val message = response.message()
            Log.v("JSON: ", message)

            return response.body()
        }catch (e: Exception){
            e.printStackTrace()
        }

        return null
    }

}