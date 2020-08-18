package com.example.android.service

import com.example.android.model.DepartmentModel
import com.example.android.model.RecordModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecordService {

    @GET("/api/records/")
    fun getRecords(): Call<ArrayList<RecordModel>>

    @GET("records/{search}/department")
    fun searchByDepartment(@Path("search") number: String) : Call<ArrayList<RecordModel>>

    @GET("records/{search}/resident")
    fun searchByResident(@Path("search") rut: String) : Call<ArrayList<RecordModel>>

    @GET("records/{search}/visit")
    fun searchByVisit(@Path("search") rut: String) : Call<ArrayList<RecordModel>>

    @POST("/api/records/")
    fun createRecord(@Query("visit_rut") visitRut: String,
                         @Query("resident_name") residentName: String,
                         @Query("department_number") departmentNumber: Int,
                         @Query("kinship") kinship: String,
                         @Query("comment") comment: String?): Call<RecordModel>

}