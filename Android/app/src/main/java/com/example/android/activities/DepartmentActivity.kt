package com.example.android.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.text.font.FontStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.android.model.DepartmentModel
import com.example.android.reponse.DepartmentResponse
import com.example.android.service.ApiService
import com.example.android.service.DepartmentService
import org.jetbrains.anko.doAsync

class DepartmentActivity : AppCompatActivity() {

    var listDepartments: ArrayList<DepartmentModel> = ArrayList()
    private val departmentService = ApiService.buildService(DepartmentService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callDepartments()

        setContent {
            MaterialTheme {
                topBar()

            }
        }
    }


    @Preview
    @Composable
    private fun topBar() {
        TopAppBar {
            Text(
                text = "List of Departments.",
                modifier = Modifier.weight(1f),
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }


    @Composable
    private fun listDepartments(departments : List<DepartmentModel>){
        VerticalScroller{
            Column {
                departments.let {
                    it.forEach { department ->
                        printDepartment(department)
                    }
                }
            }

        }

    }

    @Composable
    private fun printDepartment(department: DepartmentModel) {
        Column {
            Text(text = department.number.toString())
            Text(text = department.floor.toString())
            Text(text = department.block.toString())
        }
    }


    private fun callDepartments(): List<DepartmentModel>{

        val requestCall = departmentService.getDepartments();

        doAsync {
            requestCall.enqueue(object : Callback<DepartmentResponse> {

                override fun onResponse(call: Call<DepartmentResponse>,
                                        response: Response<DepartmentResponse>) {
                    when {
                        response.isSuccessful -> {
                            val dataList = response.body()!!.departments

                            for (i in dataList) listDepartments.add(i)

                        }
                        response.code() == 401 -> {
                            Toast.makeText(
                                    this@DepartmentActivity,
                                    "Your session has expired. Please Login again.", Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {// Application-level failure
                            // Your status code is in the range of 300's, 400's and 500's
                            Toast.makeText(
                                    this@DepartmentActivity,
                                    "Failed to retrieve items",
                                    Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                }

                override fun onFailure(call: Call<DepartmentResponse>, t: Throwable) {
                    Toast.makeText(
                            this@DepartmentActivity,
                            "Error${t.toString()}",
                            Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
        return listDepartments
    }


}