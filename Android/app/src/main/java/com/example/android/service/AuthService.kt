package com.example.android.service

import com.example.android.reponse.LoginResponse
import com.example.android.reponse.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface AuthService {

    @FormUrlEncoded
    @POST("register")
    fun register(@Field ("name") name : String,
                 @Field ("email") email: String,
                 @Field("password") password : String,
                 @Field("password_confirmation") password_confirmation: String
                ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email : String,
              @Field("password") password : String
             ): Call<LoginResponse>


}