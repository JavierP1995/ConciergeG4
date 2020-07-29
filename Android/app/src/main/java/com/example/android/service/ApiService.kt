package com.example.android.service

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ApiService {

    private const val URL = "https://192.168.0.17"
    //private const val URL = "https://127.0.0.1:8080/"


    // Create Logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Create a Custom Interceptor to apply Headers application wide
    val headerInterceptor = Interceptor { chain ->
        var request = chain.request()

        request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .build()

        val response = chain.proceed(request)
        response
    }

    private val okHttp = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(logger)

    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()


    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}