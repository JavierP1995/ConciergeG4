package com.example.android.adapter

import android.util.Log
import com.example.android.model.RecordModel
import com.example.android.model.ResidentModel
import com.example.android.service.ApiService
import com.example.android.service.RecordService
import com.example.android.service.ResidentService
import retrofit2.Call

object ResidentAdapter {

    fun loadResidents(): Collection<ResidentModel>? {
        val requestCall: Call<ArrayList<ResidentModel>> =
                ApiService.buildService(ResidentService::class.java).
                getResidents()
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun createResident(rut: String, name: String, email: String,
                     phone: Int, department: Int): ResidentModel? {
        val call: Call<ResidentModel> = ApiService
            .buildService(ResidentService::class.java)
            .createResident(rut=rut, name = name, email = email,
                phone = phone, department_id = department)
        try{
            val response = call.execute()
            Log.v("Json", response.body()!!.toString())
            return ResidentModel(rut=rut, name = name, email = email,
                phone = phone, department_id = department)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

}