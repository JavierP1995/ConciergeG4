package com.example.android.activities

import android.os.Bundle
import android.widget.Toast
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
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.material.darkColorPalette
import androidx.ui.res.imageResource
import androidx.ui.text.font.FontStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.example.android.ListVisits
import com.example.android.R
import com.example.android.adapter.RecordAdapter
import com.example.android.adapter.VisitAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.android.model.VisitModel
import com.example.android.service.ApiService
import com.example.android.service.VisitService
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VisitsActivity : AppCompatActivity() {



    private val visitsList = MutableLiveData<ListVisits>().apply {
        value = ListVisits(emptyList(), false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                listVisits(visitsList)

            }
        }

        lifecycleScope.launch {
            visitsList.value = visitsList.value?.copy(loading = true)
            val visit = withContext(Dispatchers.IO){
                VisitAdapter.loadVisits()
            }
            visitsList.value = visitsList.value?.copy(visit ?: emptyList(),
                loading = false)

        }

    }


    @Preview
    @Composable
    private fun topBar() {
        MaterialTheme(colors = darkThemeColors) {

        }
        TopAppBar(
            {
                Text(
                    text = "Visits",
                    style = MaterialTheme.typography.h5
                )
            },
            backgroundColor = darkThemeColors.primary,
            elevation = 0.dp
        )

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
    private fun listVisits(visits : LiveData<ListVisits>){

        val visit by visits.observeAsState()

        MaterialTheme(colors = darkThemeColors) {

            Scaffold(
                topBar = {
                    topBar()
                },
                bodyContent = {
                    if(visit == null || visit?.loading == true){
                        Loading()
                    } else {
                        Column() {
                            visit!!.visits.let {
                                it.forEach{visit->
                                    printVisits(visit = visit)
                                }
                            }

                        }
                    }
                }
            )
        }

    }

    @Composable
    private fun printVisits(visit: VisitModel) {
        Column(
            modifier = Modifier.padding(0.dp, 10.dp)
        )
        {
            Row() {
                Image(
                    painter = ImagePainter(imageResource(id = R.drawable.visits)),
                    modifier = Modifier.preferredSize(45.dp).clip(shape = CircleShape),
                    alignment = Alignment.TopStart
                )
                Column(
                    modifier = Modifier.padding(10.dp, 0.dp)
                ) {
                    Text(
                        text = visit.name,
                        color= darkThemeColors.onPrimary
                    )
                    Text(
                        text = visit.rut,
                        color = darkThemeColors.onPrimary
                    )
                }

            }
        }
    }

}