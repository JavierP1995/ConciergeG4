package com.example.android.service

import com.example.android.model.RecordModel
import retrofit2.Call
import retrofit2.http.GET

interface RecordService {

    @GET("records")
    fun getRecords(): Call<ArrayList<RecordModel>>

}