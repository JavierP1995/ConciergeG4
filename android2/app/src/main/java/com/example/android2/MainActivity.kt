package com.example.android2

import ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import model.Department
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.0.5:8000/api/") //192.168.0.5
            .build()

        val ApiService = retrofit.create(ApiService::class.java)

        val myCall: Call<List<Department>> = ApiService.getDepartments()

        myCall.enqueue(object :Callback<List<Department>>{
            override fun onFailure(call: Call<List<Department>>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<Department>>,
                response: Response<List<Department>>
            ) {
                val Departments:List<Department> = response.body()!!

                val stringBuilder = StringBuilder()
                for(department in Departments){
                    stringBuilder.append("Number: ")
                    stringBuilder.append(department.number)
                    stringBuilder.append("\n")
                    stringBuilder.append("Floor: ")
                    stringBuilder.append(department.floor)
                    stringBuilder.append("\n")
                    stringBuilder.append("Block: ")
                    stringBuilder.append(department.block)
                    stringBuilder.append("\n")
                    stringBuilder.append("\n")
                }
                txtDepartment.text = stringBuilder
            }
        })
    }
}