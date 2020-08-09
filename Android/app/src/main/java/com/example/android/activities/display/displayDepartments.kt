package com.example.android.activities.display

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.core.setContent
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.ListDepartments
import com.example.android.R
import com.example.android.adapter.DepartmentAdapter
import com.example.android.model.DepartmentModel
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class displayDepartments : AppCompatActivity() {
    //recibimos el bundle
    var b = intent.extras
    val option = b?.getString("option")
    val search = b?.getString("search")

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
                if( option == "all"){
                    DepartmentAdapter.loadDepartments()
                }else if(option == "byNumber"){
                    search?.let { DepartmentAdapter.loadByNumber(it) }
                }else if(option == "byResident"){
                    search?.let { DepartmentAdapter.loadByResident(it) }
                }else{
                    search?.let { DepartmentAdapter.loadByVisit(it) }
                }

            }
            departamentsList.value = departamentsList.value?.copy(dpts ?: emptyList(),
                loading = false)

        }
    }
    @Preview
    @Composable
    private fun topBar() =
            MaterialTheme( colors = darkThemeColors) {


                TopAppBar(
                        title =
                        { Text(
                                text = "Departments",
                                style = MaterialTheme.typography.h5
                                )
                        },
                        backgroundColor = darkThemeColors.primary,
                        elevation = 0.dp
                )
            }

    @Composable
    private fun listDepartments(departments : LiveData<ListDepartments>){

        val deptos by departments.observeAsState()
        MaterialTheme(colors = darkThemeColors) {
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

    }
    @Composable
    fun Loading() {
        MaterialTheme(colors = darkThemeColors) {
            Stack(modifier = Modifier.fillMaxSize()) {
                Text(
                        text = "Loading",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.gravity(Alignment.Center)
                )
            }

        }
    }
    @Composable
    private fun printDepartment(department: DepartmentModel) {
        Column (
                modifier = Modifier.padding(0.dp, 10.dp)
        ){
            Row{

                Image(
                        painter = ImagePainter(imageResource(id = R.drawable.home)),
                        modifier = Modifier.preferredSize(35.dp).clip(shape = CircleShape),
                        alignment = Alignment.TopStart
                )

                Text(
                        text = department.number.toString(),
                        color = darkThemeColors.onPrimary,
                        modifier = Modifier.padding(10.dp, 5.dp).size(40.dp)

                )
            }
            Row(
            ){
                Text(
                        text = "Floor: "+department.floor.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        color = darkThemeColors.onPrimary

                    )

                Text(
                        text = "Block: "+department.block.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        color = darkThemeColors.onPrimary,
                        modifier = Modifier.padding(20.dp,0.dp)

                        )
            }
        }
    }
}
