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
import com.example.android.ListRecords
import com.example.android.R
import com.example.android.adapter.RecordAdapter
import com.example.android.model.RecordModel
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class displayRecords : AppCompatActivity() {


    private val recordsList = MutableLiveData<ListRecords>().apply {
        value = ListRecords(emptyList(), false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(){
            MaterialTheme() {
                listRecords(recordsList)
            }
        }

        lifecycleScope.launch {
            recordsList.value = recordsList.value?.copy(loading = true)
            val records = withContext(Dispatchers.IO){
                RecordAdapter.loadRecords()
            }
            recordsList.value = recordsList.value?.copy(records ?: emptyList(),
                loading = false)

        }

    }

    @Preview
    @Composable
    private fun topBar() =
        MaterialTheme( colors = darkThemeColors) {
            TopAppBar(
                { Text(
                    text = "Records of Visits",
                    style = MaterialTheme.typography.h5
                )
                },
                backgroundColor = darkThemeColors.primary,
                elevation = 0.dp
            )
        }

    @Composable
    private fun listRecords(records : LiveData<ListRecords>){

        val record by records.observeAsState()

        MaterialTheme(colors = darkThemeColors) {
            Scaffold(
                topAppBar = {
                    topBar()
                },
                bodyContent = {
                    if(record == null || record?.loading == true){
                        Loading()
                    }else{
                        Column() {
                            record!!.records.let {
                                it.forEach { record ->
                                    printRecords(record = record)
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
    private fun printRecords(record: RecordModel) {
        Column (
            modifier = Modifier.padding(0.dp, 10.dp)
        ){
            Row{

                Image(
                    painter = ImagePainter(imageResource(id = R.drawable.user)),
                    modifier = Modifier.preferredSize(35.dp).clip(shape = CircleShape),
                    alignment = Alignment.TopStart
                )

                Text(
                    text = record.visits + " , " + record.department.toString(),
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
                    text = "Resident: "+ record.resident,
                    style = MaterialTheme.typography.subtitle2,
                    color = darkThemeColors.onPrimary
                )

            }
        }
    }

}