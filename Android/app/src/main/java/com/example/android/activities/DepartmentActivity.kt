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

    private val departmentList = MutableLiveData<ListDepartments>().apply {
        value = ListDepartments(false, emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                listDepartments(departmentList)

            }
        }

        lifecycleScope.launch {
            departmentList.value = departmentList.value?.copy(loading = true)
            val dpts = withContext(Dispatchers.IO) {
                Departments.loadDepartments()
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
    private fun listDepartments(departments: LiveData<ListDepartments>) {

        val context = ContextAmbient.current
        val resources = context.resources
        val deptos by departments.observeAsState()

        Scaffold(
            topAppBar = {
                topBar()
            },
            bodyContent = {
                if (deptos == null || deptos?.loading == true) {
                    Loading(resources)
                } else {
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
    fun Loading(resources: Resources) {
        Stack(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Loading",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.gravity(Alignment.Center)
            )
        }

    }

    @Composable
    private fun printDepartment(department: DepartmentModel) {
        Column {
            Text(text = "Number: " + department.number.toString())
            Text(text = "Floor: " + department.floor.toString())
            Text(text = "Block: " + department.block.toString())
        }
    }
}