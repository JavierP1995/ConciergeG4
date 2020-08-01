package com.example.android.adapter

import android.util.Log
import com.example.android.model.DepartmentModel
import com.example.android.service.ApiService
import com.example.android.service.DepartmentService
import retrofit2.Call

object DepartmentAdapter {

    fun loadDepartments(): Collection<DepartmentModel>? {
        val requestCall: Call<ArrayList<DepartmentModel>> =
                ApiService.buildService(DepartmentService::class.java).
                getDepartments()
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

}