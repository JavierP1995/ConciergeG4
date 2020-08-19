package com.example.android.service

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
    fun getVisits(@Header("Authorization") auth:String): Call<ArrayList<VisitModel>>

    /**
     * Get operation for visits filtered by rut
     */
    @GET("visits/{search}")
    fun searchByRut(@Header("Authorization") auth:String,
                    @Path("search") rut: String): Call<ArrayList<VisitModel>>

    /**
     * Get operation for visits filtered by resident visited in record
     */
    @GET("visits/{search}/resident")
    fun searchByResident(@Header("Authorization") auth:String,
                         @Path("search") rut: String): Call<ArrayList<VisitModel>>

    /**
     * Get operation for visits filtered by department visited in record
     */
    @GET("visits/{search}/department")
    fun searchByDepartment(@Header("Authorization") auth:String,
                           @Path("search") number: String): Call<ArrayList<VisitModel>>

    /**
     * Post operation for visits
     */
    @POST("/api/visits/")
    @Headers("Accept: application/json")
    fun createRecord(@Header("Authorization") auth:String,
                     @Query("rut") rut: String,
                     @Query("name") name: String,
                     @Query("admitted") admitted: String): Call<VisitModel>

}

