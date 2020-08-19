package com.example.android.service

import com.example.android.model.DepartmentModel
import retrofit2.Call
import retrofit2.http.*

/**
 * Api connections for departments
 */
interface DepartmentService {

    /**
     * Get operation for all departments
     */
    @GET("departments/")
    @Headers("Accept: application/json")
    fun getDepartments(@Header("Authorization") auth:String):Call<ArrayList<DepartmentModel>>

    /**
     * Get operation for departments filtered by number
     */
    @GET("departments/{search}/")
    @Headers("Accept: application/json")
    fun searchByNumber(@Header("Authorization") auth:String,
                       @Path("search") number: String) : Call<ArrayList<DepartmentModel>>

    /**
     * Get operation for departments filtered by his residents
     */
    @GET("departments/{search}/resident/")
    @Headers("Accept: application/json")
    fun searchByResident(@Header("Authorization") auth:String,
                         @Path("search") rut: String): Call<ArrayList<DepartmentModel>>

    /**
     * Get operation for departments filtered by his visits in record
     */
    @GET("departments/{search}/visit/")
    @Headers("Accept: application/json")
    fun searchByVisit(@Header("Authorization") auth:String,
                      @Path("search") rut: String): Call<ArrayList<DepartmentModel>>

    /**
     * Post operation for departments
     */
    @POST("/api/departments/")
    @Headers("Accept: application/json")
    fun createDepartment(@Header("Authorization") auth:String,
                         @Query("number") number: Int,
                         @Query("floor") floor: Int?,
                         @Query("block") block: Char?): Call<DepartmentModel>
}