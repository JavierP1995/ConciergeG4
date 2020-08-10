package com.example.android.service

import com.example.android.model.RecordModel
import com.example.android.model.ResidentModel
import com.example.android.model.VisitModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ResidentService {

    @GET("residents")
    fun getResidents(): Call<ArrayList<ResidentModel>>

    @GET("residents/{search}")
    fun searchByRut(@Path("search") rut: String): Call<ArrayList<ResidentModel>>

    @GET("residents/{search}/department")
    fun searchByDepartment(@Path("search") number: Int): Call<ArrayList<ResidentModel>>

    @GET("residents/{search}/visit")
    fun searchByVisit(@Path("search") rut: String): Call<ArrayList<ResidentModel>>

    @POST("residents")
    fun createResident(@Query("rut") rut: String?, @Query("name") name: String?,
                        @Query("email") email: String?, @Query("phone") phone: Int,
                        @Query("department_number") department_id: Int): Call<ResidentModel>
}