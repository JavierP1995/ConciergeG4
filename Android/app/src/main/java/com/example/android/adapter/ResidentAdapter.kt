package com.example.android.adapter

import android.util.Log
import com.example.android.model.ResidentModel
import com.example.android.service.ApiService
import com.example.android.service.ResidentService
import retrofit2.Call

object ResidentAdapter {

    fun loadResidents(token : String): Collection<ResidentModel>? {
        val requestCall: Call<ArrayList<ResidentModel>> =
                ApiService.buildService(ResidentService::class.java).
                getResidents("Bearer $token")
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun loadByDepartment(token : String, number: String): Collection<ResidentModel>? {
        val requestCall: Call<ArrayList<ResidentModel>> =
            ApiService.buildService(ResidentService::class.java).
            searchByDepartment("Bearer $token", number = number)
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun loadByRut(token: String, rut: String): Collection<ResidentModel>? {
        val requestCall: Call<ArrayList<ResidentModel>> =
            ApiService.buildService(ResidentService::class.java).
            searchByRut("Bearer $token", rut = rut)
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun loadByVisit(token : String, rut: String): Collection<ResidentModel>? {
        val requestCall: Call<ArrayList<ResidentModel>> =
            ApiService.buildService(ResidentService::class.java).
            searchByVisit("Bearer $token", rut = rut)
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun createResident(token : String, rut: String, name: String, email: String,
                     phone: Int, department: Int): ResidentModel? {
        val call: Call<ResidentModel> = ApiService
            .buildService(ResidentService::class.java)
            .createResident("Bearer $token", rut=rut, name = name, email = email,
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