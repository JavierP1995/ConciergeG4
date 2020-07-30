package com.example.android.service

import com.example.android.model.ResidentModel
import retrofit2.Call
import retrofit2.http.GET

interface ResidentService {

    @GET("/api/residents/")
    fun getResidents(): Call<ArrayList<ResidentModel>>

}