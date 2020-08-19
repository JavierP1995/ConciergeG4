package com.example.android.service

import com.example.android.model.ResidentModel
import retrofit2.Call
import retrofit2.http.*

/**
 * Api connections for residents
 */
interface ResidentService {

    /**
     * Get operation for all residents
     */
    @GET("residents")
    fun getResidents(@Header("Authorization") auth:String): Call<ArrayList<ResidentModel>>

    /**
     * Get operation for residents filtered by rut
     */
    @GET("residents/{search}")
    fun searchByRut(@Header("Authorization") auth:String,
                    @Path("search") rut: String): Call<ArrayList<ResidentModel>>

    /**
     * Get operation for residents filtered by his department number
     */
    @GET("residents/{search}/department")
    fun searchByDepartment(@Header("Authorization") auth:String,
                           @Path("search") number: String): Call<ArrayList<ResidentModel>>

    /**
     * Get operation for residents filtered by visits in record
     */
    @GET("residents/{search}/visit")
    fun searchByVisit(@Header("Authorization") auth:String,
                      @Path("search") rut: String): Call<ArrayList<ResidentModel>>

    /**
     * Post operation for residents
     */
    @POST("residents")
    fun createResident(@Header("Authorization") auth:String,
                       @Query("rut") rut: String?, @Query("name") name: String?,
                       @Query("email") email: String?, @Query("phone") phone: Int,
                       @Query("department_number") department_id: Int): Call<ResidentModel>
}