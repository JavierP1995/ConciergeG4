package com.example.android.adapter

import android.util.Log
import com.example.android.model.RecordModel
import com.example.android.service.ApiService
import com.example.android.service.RecordService
import retrofit2.Call

object RecordAdapter {

    fun loadRecords(token: String): Collection<RecordModel>? {
        val requestCall: Call<ArrayList<RecordModel>> =
                ApiService.buildService(RecordService::class.java).
                getRecords("Bearer $token")
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun loadByDepartment(token : String , number: String): Collection<RecordModel>? {
        val requestCall: Call<ArrayList<RecordModel>> =
                ApiService.buildService(RecordService::class.java).
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

    fun loadByResident(token : String, rut: String): Collection<RecordModel>? {
        val requestCall: Call<ArrayList<RecordModel>> =
                ApiService.buildService(RecordService::class.java).
                searchByResident("Bearer $token", rut = rut)
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun loadByVisit(token : String, rut: String): Collection<RecordModel>? {
        val requestCall: Call<ArrayList<RecordModel>> =
                ApiService.buildService(RecordService::class.java).
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

    fun createRecord(token : String, visitRut: String, departmentNumber: Int, residentName: String,
                       kinship: String, comment: String): RecordModel? {
        val call: Call<RecordModel> = ApiService
            .buildService(RecordService::class.java).createRecord("Bearer $token", visitRut=visitRut,
                departmentNumber = departmentNumber, residentName = residentName,
                    kinship = kinship, comment = comment)
        try{
            val response = call.execute()
            Log.v("Json", response.body()!!.toString())
            return RecordModel(kinship = kinship, entryDate = null, departureDate = null,
                                comment = comment, resident = residentName, visits = visitRut,
                                    department = departmentNumber)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}