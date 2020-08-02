package com.example.android.service

import com.example.android.model.DepartmentModel
import retrofit2.Call
import retrofit2.http.GET

interface DepartmentService {
    @GET("departments/")
    fun getDepartments():Call<ArrayList<DepartmentModel>>
}