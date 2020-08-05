package com.example.android.adapter

import android.util.Log
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
}