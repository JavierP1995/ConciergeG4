package com.example.android.activities.display

import android.graphics.drawable.PaintDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.*
import androidx.ui.core.Alignment.Companion.Center
import androidx.ui.core.Alignment.Companion.TopCenter
import androidx.ui.core.Alignment.Companion.TopStart
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.geometry.Offset
import androidx.ui.geometry.Size
import androidx.ui.graphics.RectangleShape
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.graphics.painter.Painter
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.ListDepartments
import com.example.android.R
import com.example.android.activities.search.SearchDepartment
import com.example.android.adapter.DepartmentAdapter
import com.example.android.model.DepartmentModel
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Class DisplayDepartments, activity that show the list of departments in the data base.
 */
class DisplayDepartments : AppCompatActivity() {

    /**
     * List of departments, initially empty.
     */
    private val departmentsList = MutableLiveData<ListDepartments>().apply {
        value = ListDepartments(emptyList(), false)
    }


    companion object{
        /**
         * Global variable used to receive and send the token in the methods.
         */
        var token : String = ""

        /**
         * Method used to change the token value
         */
        fun setLoginData(authToken : String){
            this.token = authToken
            Log.v("TOKEN", this.token)
        }
    }

    /**
     * Method used to start the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Received the bundle
        val bundle :Bundle ?= intent.extras
        val option = bundle!!.getString("option")
        val search = bundle!!.getString("search")
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                listDepartments(departmentsList)

            }
        }

        lifecycleScope.launch {
            departmentsList.value = departmentsList.value?.copy(loading = true)
            val dpts = withContext(Dispatchers.IO){
                if( option == "all"){
                    DepartmentAdapter.loadDepartments(token)
                }else if(option == "byNumber"){
                    search?.let { DepartmentAdapter.loadByNumber(token, it) }
                }else if(option == "byResident"){
                    search?.let { DepartmentAdapter.loadByResident(token, it) }
                }else{
                    search?.let { DepartmentAdapter.loadByVisit(token, it) }
                }

            }
            departmentsList.value = departmentsList.value?.copy(dpts ?: emptyList(),
                loading = false)

        }
    }


    /**
     * Method used to print the list of departments.
     */
    @Composable
    private fun listDepartments(departments : LiveData<ListDepartments>){

        val deptos by departments.observeAsState()
        MaterialTheme(colors = darkThemeColors) {
            Scaffold(
                    topAppBar = {
                        TopAppBar(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                    painter = ImagePainter(imageResource(id = R.drawable.menudepartments)),
                                    contentScale = ContentScale.FillWidth

                            )

                        }
                    },

                    bodyContent = {
                        if(deptos == null || deptos?.loading == true){
                            Loading()
                        }else{
                            Column() {
                                VerticalScroller(modifier = Modifier.fillMaxWidth()) {
                                    deptos!!.departments.let {
                                        it.forEach { department ->
                                            printDepartment(department = department)
                                        }
                                    }
                                }

                            }
                        }
                    }
            )
        }

    }

    /**
     * Method the used to show screen "loading" while the data is load.
     */
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

    /**
     * Method used to print attributes of department.
     */
    @Composable
    private fun printDepartment(department: DepartmentModel) {

        Column (
                modifier = Modifier.padding(0.dp, 10.dp)
        ){
            Row{

                Image(
                        painter = ImagePainter(imageResource(id = R.drawable.iconhome)),
                        modifier = Modifier.preferredSize(35.dp),
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
            Divider()
        }
    }

}
