package com.example.android.service

import com.example.android.model.RecordModel
import com.example.android.model.ResidentModel
import com.example.android.model.VisitModel
import retrofit2.Call
import retrofit2.http.*

interface ResidentService {

    @GET("residents")
    fun getResidents(@Header("Authorization") auth:String): Call<ArrayList<ResidentModel>>

    @GET("residents/{search}")
    fun searchByRut(@Header("Authorization") auth:String,
                    @Path("search") rut: String): Call<ArrayList<ResidentModel>>

    @GET("residents/{search}/department")
    fun searchByDepartment(@Header("Authorization") auth:String,
                           @Path("search") number: String): Call<ArrayList<ResidentModel>>

    @GET("residents/{search}/visit")
    fun searchByVisit(@Header("Authorization") auth:String,
                      @Path("search") rut: String): Call<ArrayList<ResidentModel>>

    @POST("residents")
    fun createResident(@Header("Authorization") auth:String,
                       @Query("rut") rut: String?, @Query("name") name: String?,
                       @Query("email") email: String?, @Query("phone") phone: Int,
                       @Query("department_number") department_id: Int): Call<ResidentModel>
}