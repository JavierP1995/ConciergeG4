package com.example.android.adapter

import com.example.android.APIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author Chirinoxi
 * This static class holds all settings for Retrofit
 *
 */
object DepartmentAdapter{

        val departmentAdapter: APIService = Retrofit.Builder()
            .baseUrl("https://localhost:8000")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
}