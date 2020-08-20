package com.example.android.service

import com.example.android.model.RecordModel
import com.example.android.model.VisitModel
import retrofit2.Call
import retrofit2.http.*

/**
 * Api connections for visits
 */
interface VisitService {

    /**
     * Get operation for all visits
     */
    @GET("visits")
    @Headers("Accept: application/json")
    fun getVisits(@Header("Authorization") auth:String): Call<ArrayList<VisitModel>>

    /**
     * Get operation for visits filtered by rut
     */
    @GET("visits/{search}")
    @Headers("Accept: application/json")
    fun searchByRut(@Header("Authorization") auth:String,
                    @Path("search") rut: String): Call<ArrayList<VisitModel>>

    /**
     * Get operation for visits filtered by resident visited in record
     */
    @GET("visits/{search}/resident")
    @Headers("Accept: application/json")
    fun searchByResident(@Header("Authorization") auth:String,
                         @Path("search") rut: String): Call<ArrayList<VisitModel>>

    /**
     * Get operation for visits filtered by department visited in record
     */
    @GET("visits/{search}/department")
    @Headers("Accept: application/json")
    fun searchByDepartment(@Header("Authorization") auth:String,
                           @Path("search") number: String): Call<ArrayList<VisitModel>>

    @PUT("visits/{rut}")
    @Headers("Accept: application/json")
    fun banVisit(@Header("Authorization") auth:String,
                       @Path("rut") rut: String): Call<VisitModel>

    /**
     * Post operation for visits
     */
    @POST("visits/")
    @Headers("Accept: application/json")
    fun createVisit(@Header("Authorization") auth:String,
                     @Query("rut") rut: String,
                     @Query("name") name: String,
                     @Query("admitted") admitted: String): Call<VisitModel>

}

