package com.example.android.service

import com.example.android.model.VisitModel
import retrofit2.Call
import retrofit2.http.GET


interface VisitService {

    @GET("visits")
    fun getVisits(): Call<ArrayList<VisitModel>>

}

