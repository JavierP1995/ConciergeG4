package com.example.android.service

import android.util.Log
import com.example.android.model.DepartmentModel
import com.example.android.reponse.DepartmentResponse
import retrofit2.Call


object Departments {

    fun loadDepartments(): Collection<DepartmentModel>? {

        val departmentService = ApiService.buildService(DepartmentService::class.java)
        val requestCall: Call<DepartmentResponse> = departmentService.getDepartments()

        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()?.departments.toString())
            return response.body()?.departments

        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

}