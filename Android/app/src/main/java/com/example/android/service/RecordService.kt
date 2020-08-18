package com.example.android.service

import com.example.android.model.DepartmentModel
import com.example.android.model.RecordModel
import retrofit2.Call
import retrofit2.http.*

interface RecordService {

    @GET("/api/records/")
    fun getRecords(@Header("Authorization") auth:String): Call<ArrayList<RecordModel>>

    @GET("records/{search}/department")
    fun searchByDepartment(@Header("Authorization") auth:String,
                           @Path("search") number: String) : Call<ArrayList<RecordModel>>

    @GET("records/{search}/resident")
    fun searchByResident(@Header("Authorization") auth:String,
                         @Path("search") rut: String) : Call<ArrayList<RecordModel>>

    @GET("records/{search}/visit")
    fun searchByVisit(@Header("Authorization") auth:String,
                      @Path("search") rut: String) : Call<ArrayList<RecordModel>>

    @POST("/api/records/")
    fun createRecord(@Header("Authorization") auth:String,
                     @Query("visit_rut") visitRut: String,
                     @Query("resident_name") residentName: String,
                     @Query("department_number") departmentNumber: Int,
                     @Query("kinship") kinship: String,
                     @Query("comment") comment: String?): Call<RecordModel>

}