package com.example.android.service

import com.example.android.model.RecordModel
import com.example.android.model.ResidentModel
import com.example.android.model.VisitModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface VisitService {

    @GET("visits")
    fun getVisits(): Call<ArrayList<VisitModel>>

    @GET("visits/{search}")
    fun searchByRut(@Path("search") rut: String): Call<ArrayList<VisitModel>>

    @GET("visits/{search}/resident")
    fun searchByResident(@Path("search") rut: String): Call<ArrayList<VisitModel>>

    @GET("visits/{search}/department")
    fun searchByDepartment(@Path("search") number: String): Call<ArrayList<VisitModel>>

}

