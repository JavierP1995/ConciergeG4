package com.example.android.activities.display

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.*
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.ListResidents
import com.example.android.R
import com.example.android.adapter.DepartmentAdapter
import com.example.android.adapter.ResidentAdapter
import com.example.android.model.ResidentModel
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Class DisplayResidents, activity that show the list of residents in DB.
 */
class DisplayResidents : AppCompatActivity() {

    /**
     * List of residents, initially empty.
     */
    private val residentsList = MutableLiveData<ListResidents>().apply {
        value = ListResidents(emptyList(), false)
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
     * Method used to star the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle :Bundle ?= intent.extras
        val option = bundle!!.getString("option")
        val search = bundle!!.getString("search")
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                listResidents(residentsList)

            }
        }

        lifecycleScope.launch {
            residentsList.value = residentsList.value?.copy(loading = true)
            val residents = withContext(Dispatchers.IO) {
                if( option == "all"){
                    ResidentAdapter.loadResidents(token)
                }else if(option == "byRut"){
                    search?.let { ResidentAdapter.loadByRut(token, it) }
                }else if(option == "byVisit"){
                    search?.let { ResidentAdapter.loadByVisit(token, it) }
                }else{
                    search?.let { ResidentAdapter.loadByDepartment(token, it) }
                }
            }
            residentsList.value = residentsList.value?.copy(residents ?: emptyList(),
                    loading = false)

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
     * Method used to print the list of residents.
     */
    @Composable
    private fun listResidents(residents: LiveData<ListResidents>) {

        val resident by residents.observeAsState()

        MaterialTheme(colors = darkThemeColors) {

            Scaffold(
                    topAppBar = {
                        TopAppBar(modifier = Modifier.fillMaxWidth()) {

                            Image(painter = ImagePainter(imageResource(id = R.drawable.menuresidents)),
                                    contentScale = ContentScale.FillWidth
                            )

                        }
                    },
                    bodyContent = {
                        if (resident == null || resident?.loading == true) {
                            Loading()
                        } else {
                            Column() {
                                VerticalScroller(modifier = Modifier.fillMaxWidth()) {
                                    resident!!.residents.let {
                                        it.forEach { resident ->
                                            printResident(resident = resident)
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
     * Method used to print attributes of residents.
     */
    @Composable
    private fun printResident(resident: ResidentModel) {
        Column(
                modifier = Modifier.padding(0.dp, 10.dp)
        )
        {
            Row() {
                Image(
                        painter = ImagePainter(imageResource(id = R.drawable.iconuser)),
                        modifier = Modifier.preferredSize(45.dp).clip(shape = CircleShape),
                        alignment = Alignment.TopStart
                )
                Text(
                        text = resident.name,
                        color = darkThemeColors.onPrimary,
                        modifier = Modifier.padding(10.dp, 5.dp)
                )
            }
            Column(modifier = Modifier.padding(30.dp, 0.dp)) {
                Text(
                        text = "Rut: " + resident.rut,
                        style = MaterialTheme.typography.subtitle2,
                        color = darkThemeColors.onPrimary,
                        modifier = Modifier.padding(30.dp, 0.dp)
                )
                Text(
                        text = "Email: " + resident.email,
                        style = MaterialTheme.typography.subtitle2,
                        color = darkThemeColors.onPrimary,
                        modifier = Modifier.padding(30.dp, 0.dp)
                )
                Text(
                        text = "Phone: " + resident.phone.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        color = darkThemeColors.onPrimary,
                        modifier = Modifier.padding(30.dp, 0.dp)
                )
            }

        }
        Divider()
    }
}
