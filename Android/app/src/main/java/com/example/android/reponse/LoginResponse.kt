package com.example.android.reponse

import com.example.android.model.UserModel

data class LoginResponse(val message: String, val user: UserModel, val token: String, val tokenExpiresAt: String)
