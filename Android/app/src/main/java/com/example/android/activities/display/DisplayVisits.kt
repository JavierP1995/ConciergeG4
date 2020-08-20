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
import androidx.ui.foundation.Border
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.ListVisits
import com.example.android.R
import com.example.android.adapter.RecordAdapter
import com.example.android.adapter.VisitAdapter
import com.example.android.model.VisitModel
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Class DisplayVisits, activity that show the list of visits in data base.
 */
class DisplayVisits : AppCompatActivity() {

    /**
     * List of visits, initially empty.
     */
    private val visitsList = MutableLiveData<ListVisits>().apply {
        value = ListVisits(emptyList(), false)
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
                listVisits(visitsList)

            }
        }

        lifecycleScope.launch {
            visitsList.value = visitsList.value?.copy(loading = true)
            val visit = withContext(Dispatchers.IO){
                if( option == "all"){
                    VisitAdapter.loadVisits(token)
                }else if(option == "byRut"){
                    search?.let { VisitAdapter.loadByRut(token, it) }
                }else if(option == "byResident"){
                    search?.let { VisitAdapter.loadByResident(token, it) }
                }else{
                    search?.let { VisitAdapter.loadByDepartment(token, it) }
                }
            }
            visitsList.value = visitsList.value?.copy(visit ?: emptyList(),
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
     * Method used to print the list of visits.
     */
    @Composable
    private fun listVisits(visits : LiveData<ListVisits>){

        val visit by visits.observeAsState()

        MaterialTheme(colors = darkThemeColors) {

            Scaffold(
                topAppBar = {
                    TopAppBar(modifier = Modifier.fillMaxWidth()) {

                        Image(painter = ImagePainter(imageResource(id = R.drawable.menuvisits)),
                                contentScale = ContentScale.FillWidth
                        )

                    }
                },
                bodyContent = {
                    if(visit == null || visit?.loading == true){
                        Loading()
                    } else {
                        Column() {
                            VerticalScroller(modifier = Modifier.fillMaxWidth()) {
                                visit!!.visits.let {
                                    it.forEach{visit->
                                        printVisits(visit = visit)
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
     * Method used to print attributes of visits.
     */
    @Composable
    private fun printVisits(visit: VisitModel) {
        Column(
            modifier = Modifier.padding(0.dp, 10.dp)
        )
        {
            Row() {
                Image(
                    painter = ImagePainter(imageResource(id = R.drawable.iconusers)),
                    modifier = Modifier.preferredSize(45.dp).clip(shape = CircleShape),
                    alignment = Alignment.TopStart
                )
                if(visit.admitted == "yes")
                {
                    Button(
                            text = {
                                Text("Ban", color = darkThemeColors.onPrimary,
                                        style = MaterialTheme.typography.subtitle2)
                            },
                            backgroundColor = Color.Transparent,
                            border = Border(2.dp, darkThemeColors.secondary),
                            modifier = Modifier.size(120.dp, 40.dp).padding(5.dp, 0.dp),
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                lifecycleScope.launch {
                                    val visit = withContext(Dispatchers.IO){
                                        VisitAdapter.banVisit(DisplayRecords.token, visit.rut)
                                    }
                                    visitsList.value = visitsList.value?.copy(loading = true)
                                    val visits = withContext(Dispatchers.IO){
                                        VisitAdapter.loadVisits(DisplayRecords.token)
                                    }
                                    visitsList.value = visitsList.value?.copy(visits ?: emptyList(),
                                            loading = false)
                                }
                            }
                    )
                }
                Column(
                    modifier = Modifier.padding(10.dp, 0.dp)
                ) {
                    Text(
                        text = "Name: " + visit.name,
                        color= darkThemeColors.onPrimary
                    )
                    Text(
                        text = "Rut: " + visit.rut,
                        color = darkThemeColors.onPrimary
                    )
                    Text(
                        text = "Admitted: " + visit.admitted,
                        color = darkThemeColors.onPrimary
                    )
                }

            }
        }
        Divider()
    }

}