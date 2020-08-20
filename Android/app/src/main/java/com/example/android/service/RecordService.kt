package com.example.android.service

import com.example.android.model.RecordModel
import com.example.android.reponse.RecordResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Api connections for records
 */
interface RecordService {

    /**
     * Get operation for all records
     */
    @GET("records/")
    @Headers("Accept: application/json")
    fun getRecords(@Header("Authorization") auth:String): Call<ArrayList<RecordModel>>

    /**
     * Get operation for records of a specified department
     */
    @GET("records/{search}/department")
    @Headers("Accept: application/json")
    fun searchByDepartment(@Header("Authorization") auth:String,
                           @Path("search") number: String) : Call<ArrayList<RecordModel>>

    /**
     * Get operation for records of a specified resident
     */
    @GET("records/{search}/resident")
    @Headers("Accept: application/json")
    fun searchByResident(@Header("Authorization") auth:String,
                         @Path("search") rut: String) : Call<ArrayList<RecordModel>>

    /**
     * Get operation for records of a specified visit
     */
    @GET("records/{search}/visit")
    @Headers("Accept: application/json")
    fun searchByVisit(@Header("Authorization") auth:String,
                      @Path("search") rut: String) : Call<ArrayList<RecordModel>>

    /**
     * Post operation for records
     */
    @FormUrlEncoded
    @POST("records/")
    @Headers("Accept: application/json")
    fun createRecord(@Header("Authorization") auth:String,
                     @Field("visit_rut") visitRut: String,
                     @Field("resident_name") residentName: String,
                     @Field("department_number") departmentNumber: Int,
                     @Field("kinship") kinship: String,
                     @Field("comment") comment: String?): Call<RecordResponse>

}