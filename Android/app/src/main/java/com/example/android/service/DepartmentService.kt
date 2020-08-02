package com.example.android.service

import com.example.android.model.DepartmentModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DepartmentService {
    @GET("departments/")
    fun getDepartments():Call<ArrayList<DepartmentModel>>

    @POST("/api/departments/")
    fun createDepartment(@Query("number") number: Int, @Query("floor") floor: Int? ,@Query("block") block: Char?): Call<DepartmentModel>
}