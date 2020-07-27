package com.example.android

import com.example.android.model.DepartmentModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Chirinoxi
 */
interface ApiService {
    @GET("departments")
    suspend fun getDepartments(): Response<DepartmentModel>
}