package com.example.android.service

import com.example.android.model.DepartmentModel
import retrofit2.Call

object Departments {

    fun loadDepartments(): List<DepartmentModel>? {

        val departmentService = ApiService.buildService(DepartmentService::class.java)
        val requestCall: Call<ArrayList<DepartmentModel>> = departmentService.getDepartments();

        try{
            val response = requestCall.execute()
            return response.body()!!

        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

}