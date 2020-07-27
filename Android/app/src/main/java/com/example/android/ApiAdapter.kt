package com.example.android

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author Chirinoxi
 * This static class holds all settings for Retrofit
 *
 */
object ApiAdapter{

        val DEPARTMENT_ADAPTER: ApiService = Retrofit.Builder()
            .baseUrl("http://localhost:8000/api/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}