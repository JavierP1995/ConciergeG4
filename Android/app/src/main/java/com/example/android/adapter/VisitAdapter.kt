package com.example.android.adapter

import android.util.Log
import com.example.android.model.RecordModel
import com.example.android.model.VisitModel
import com.example.android.service.ApiService
import com.example.android.service.RecordService
import com.example.android.service.VisitService
import retrofit2.Call

object VisitAdapter {

    fun loadVisits(token : String): Collection<VisitModel>? {
        val requestCall: Call<ArrayList<VisitModel>> =
                ApiService.buildService(VisitService::class.java).
                getVisits("Bearer $token")
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun loadByDepartment(token : String, number: String): Collection<VisitModel>? {
        val requestCall: Call<ArrayList<VisitModel>> =
            ApiService.buildService(VisitService::class.java).
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

    fun loadByRut(token : String, rut: String): Collection<VisitModel>? {
        val requestCall: Call<ArrayList<VisitModel>> =
            ApiService.buildService(VisitService::class.java).
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

    fun loadByResident(token : String, rut: String): Collection<VisitModel>? {
        val requestCall: Call<ArrayList<VisitModel>> =
            ApiService.buildService(VisitService::class.java).
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

    /**
     * This method allows us to call the createRecord method of the VisitService object.
     */
    fun createVisit(name: String, rut: String, admitted: String): VisitModel? {

        val call: Call<VisitModel> = ApiService
                .buildService(VisitService::class.java).createRecord(name=name,
                        rut = rut, admitted = admitted)
        try{
            val response = call.execute()
            Log.v("Json", response.body()!!.toString())
            return VisitModel(name = name, rut = rut, admitted = admitted)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}