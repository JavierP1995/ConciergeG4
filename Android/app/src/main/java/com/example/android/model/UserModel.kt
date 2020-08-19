package com.example.android.model

/**
 * User representation in android
 */
data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val remember_token: String
)