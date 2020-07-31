package com.example.android.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.text.font.FontStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.android.model.VisitModel
import com.example.android.service.ApiService
import com.example.android.service.VisitService

class VisitsActivity : AppCompatActivity() {

    var listVisits: ArrayList<VisitModel> = ArrayList()
    private val visitService = ApiService.buildService(VisitService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                callVisits()
                topBar()

            }
        }
    }


    @Preview
    @Composable
    private fun topBar() {
        TopAppBar {
            Text(
                    text = "List of Visits",
                    modifier = Modifier.weight(1f),
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
            )
        }
    }


    @Composable
    private fun listVisits(visits : List<VisitModel>){
        VerticalScroller{
            Column {
                visits.let {
                    it.forEach { visit ->
                        printVisits(visit)
                    }
                }
            }

        }

    }

    @Composable
    private fun printVisits(visit: VisitModel) {
        Column {
            Text(text = visit.name.toString())
            Text(text = visit.rut.toString())
            Text(text = visit.admitted.toString())
        }
    }

    private fun callVisits(): List<VisitModel> {

        val requestCall: Call<ArrayList<VisitModel>> = visitService.getVisits();
        
        requestCall.enqueue(object : Callback<ArrayList<VisitModel>> {

            override fun onResponse(
                    call: Call<ArrayList<VisitModel>>,
                    response: Response<ArrayList<VisitModel>>
            ) {
                when {
                    response.isSuccessful -> {
                        val dataList = response.body()!!

                        listVisits = dataList
                    }
                    response.code() == 401 -> {
                        Toast.makeText(
                                this@VisitsActivity,
                                "Your session has expired. Please Login again.", Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {// Application-level failure
                        // Your status code is in the range of 300's, 400's and 500's
                        Toast.makeText(
                                this@VisitsActivity,
                                "Failed to retrieve items",
                                Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<VisitModel>>, t: Throwable) {
                Toast.makeText(
                        this@VisitsActivity,
                        "Error${t.toString()}",
                        Toast.LENGTH_LONG
                ).show()
            }
        })

        return listVisits
    }

}