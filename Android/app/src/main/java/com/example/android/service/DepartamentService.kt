package com.example.android.service

import com.example.android.model.DepartmentModel
import com.example.android.reponse.DepartmentResponse
import retrofit2.Call
import retrofit2.http.*

interface DepartmentService {

    @GET("/api/departments/")
    fun getDepartments(): Call<DepartmentResponse>

    @POST("/api/departments/")
    fun createDepartment(@Query("number") number: Int? , @Query("block") block: Char?, @Query("floor") floor: Int?): Call<DepartmentModel>

}