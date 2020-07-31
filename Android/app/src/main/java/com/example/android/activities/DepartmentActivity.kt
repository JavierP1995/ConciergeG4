package com.example.android.activities

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize
import androidx.ui.livedata.observeAsState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.text.font.FontStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.sp
import com.example.android.ListDepartments
import com.example.android.model.DepartmentModel
import com.example.android.service.Departments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DepartmentActivity : AppCompatActivity() {

    private val departamentsList = MutableLiveData<ListDepartments>().apply {
        value = ListDepartments(emptyList(), false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                listDepartments(departamentsList)

            }
        }

        lifecycleScope.launch {
            departamentsList.value = departamentsList.value?.copy(loading = true)
            val dpts = withContext(Dispatchers.IO){
                Departments.loadDepartments()
            }
            departamentsList.value = departamentsList.value?.copy(dpts ?: emptyList(),
                loading = false)

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
    private fun listDepartments(departments : LiveData<ListDepartments>){

        val deptos by departments.observeAsState()

        Scaffold(
            topAppBar = {
                topBar()
            },
            bodyContent = {
                if(deptos == null || deptos?.loading == true){
                    Loading()
                }else{
                    Column() {
                        deptos!!.departments.let {
                            it.forEach { department ->
                                    printDepartment(department = department)
                                }
                        }
                    }
                }
            }
        )

    }
    @Composable
    fun Loading() {
        Stack(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Nada aquí",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.gravity(Alignment.Center)
            )
        }

    }
    @Composable
    private fun printDepartment(department: DepartmentModel) {
        Column {
            Text(text = "Number: "+department.number.toString())
            Text(text = "Floor: "+department.floor.toString())
            Text(text = "Block: "+department.block.toString())
        }
    }

    /**private fun callDepartments(): ArrayList<DepartmentModel> {

    val departmentService = ApiService.buildService(DepartmentService::class.java)

    val requestCall: Call<ArrayList<DepartmentModel>> = departmentService.getDepartments();

    val response = requestCall.execute()

    if(!response.isSuccessful){
    // Your status code is in the range of 300's, 400's and 500's
    Toast.makeText(
    this@DepartmentActivity,
    "Failed to retrieve items",
    Toast.LENGTH_LONG
    ).show()
    }
    else if( response.code() == 401) {
    Toast.makeText(
    this@DepartmentActivity,
    "Your session has expired. Please Login again.", Toast.LENGTH_LONG
    ).show()
    }
    return response.body()!!
    }*/
}