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
import com.example.android.ListRecords
import com.example.android.R
import com.example.android.adapter.DepartmentAdapter
import com.example.android.adapter.RecordAdapter
import com.example.android.model.RecordModel
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Class DisplayRecords, activity that show the list of records in data base.
 */
class DisplayRecords : AppCompatActivity() {

    /**
     * List of records, initially empty.
     */
    private val recordsList = MutableLiveData<ListRecords>().apply {
        value = ListRecords(emptyList(), false)
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
        setContent(){

            listRecords(recordsList)

        }

        lifecycleScope.launch {
            recordsList.value = recordsList.value?.copy(loading = true)
            val records = withContext(Dispatchers.IO){
                if( option == "all"){
                    RecordAdapter.loadRecords(token)
                }else if(option == "byDepartment"){
                    search?.let { RecordAdapter.loadByDepartment(token, it) }
                }else if(option == "byResident"){
                    search?.let { RecordAdapter.loadByResident(token, it) }
                }else{
                    search?.let { RecordAdapter.loadByVisit(token, it) }
                }
            }
            recordsList.value = recordsList.value?.copy(records ?: emptyList(),
                loading = false)

        }

    }

    /**
     * Method used to print the list of records.
     */
    @Composable
    private fun listRecords(records : LiveData<ListRecords>){

        val record by records.observeAsState()

        MaterialTheme(colors = darkThemeColors) {
            Scaffold(
                topAppBar = {
                    TopAppBar(modifier = Modifier.fillMaxWidth()) {

                        Image(painter = ImagePainter(imageResource(id = R.drawable.menurecords)),
                                contentScale = ContentScale.FillWidth
                        )

                    }
                },
                bodyContent = {
                    if(record == null || record?.loading == true){
                        Loading()
                    }else{
                        Column() {
                            VerticalScroller(modifier = Modifier.fillMaxWidth()) {
                                record!!.records.let {
                                    it.forEach { record ->
                                        printRecords(record = record)
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
     * Method used to print attributes of records.
     */
    @Composable
    private fun printRecords(record: RecordModel) {
        Column (
            modifier = Modifier.padding(0.dp, 10.dp)
        ){
            Row{

                Image(
                    painter = ImagePainter(imageResource(id = R.drawable.iconregister)),
                    modifier = Modifier.preferredSize(35.dp),
                    alignment = Alignment.TopStart
                )

                Text(
                    text = record.resident + " , " + record.department.toString(),
                    color = darkThemeColors.onPrimary,
                    modifier = Modifier.padding(10.dp, 5.dp)
                )
            }
            Column(
                modifier = Modifier.padding(10.dp, 3.dp)
            ){
                Text(
                    text = "Entry: "+ record.entryDate.toString(),
                    style = MaterialTheme.typography.subtitle2,
                    color = darkThemeColors.onPrimary

                )

                Text(
                    text = "Departure: "+ record.departureDate.toString(),
                    style = MaterialTheme.typography.subtitle2,
                    color = darkThemeColors.onPrimary

                )

                Text(
                    text = "Visit: "+ record.visit,
                    style = MaterialTheme.typography.subtitle2,
                    color = darkThemeColors.onPrimary
                )

                Text(
                    text = "Comment: "+ record.comment,
                    style = MaterialTheme.typography.subtitle2,
                    color = darkThemeColors.onPrimary
                )

            }
        }
        Divider()
    }

}