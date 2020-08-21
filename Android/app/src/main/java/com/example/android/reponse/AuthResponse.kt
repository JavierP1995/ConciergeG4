package com.example.android.reponse

import com.example.android.model.UserModel

data class LoginResponse(val message: String, val user: UserModel, val token: String, val token_expires_at: String)

data class LogoutResponse(val message: String)

data class RegisterResponse(val message: String?, val user : UserModel, val token: String, val token_expires_at: String)