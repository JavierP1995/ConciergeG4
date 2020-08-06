package com.example.android.adapter

import android.util.Log
import android.widget.Toast
import com.example.android.model.RecordModel
import com.example.android.service.ApiService
import com.example.android.service.RecordService
import retrofit2.Call

object RecordAdapter {

    fun loadRecords(): Collection<RecordModel>? {
        val requestCall: Call<ArrayList<RecordModel>> =
                ApiService.buildService(RecordService::class.java).
                getRecords()
        try{
            val response = requestCall.execute()
            Log.v("Json", response.body()!!.toString())
            return response.body()!!
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    fun createRecord(visitRut: String, departmentNumber: Int, residentName: String,
                       kinship: String, comment: String): RecordModel? {
        val call: Call<RecordModel> = ApiService
            .buildService(RecordService::class.java).createRecord(visitRut=visitRut,
                departmentNumber = departmentNumber, residentName = residentName,
                    kinship = kinship, comment = comment)
        try{
            val response = call.execute()
            Log.v("Json", response.body()!!.toString())
            return RecordModel(kinship = kinship, entryDate = null, departureDate = null,
                                comment = comment, resident = residentName, visits = visitRut,
                                    department = departmentNumber)
            //FIXME: Verificar si el objeto debe tener la id de departamento (como en la bd).
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}