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
import com.example.android.ListResidents
import com.example.android.R
import com.example.android.adapter.ResidentAdapter
import com.example.android.model.ResidentModel
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class displayResidents : AppCompatActivity() {

    private val residentsList = MutableLiveData<ListResidents>().apply {
        value = ListResidents(emptyList(), false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                listResidents(residentsList)

            }
        }

        lifecycleScope.launch {
            residentsList.value = residentsList.value?.copy(loading = true)
            val residents = withContext(Dispatchers.IO) {
                ResidentAdapter.loadResidents()
            }
            residentsList.value = residentsList.value?.copy(residents ?: emptyList(),
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
                            text = "Residents",
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
    private fun listResidents(residents: LiveData<ListResidents>) {

        val resident by residents.observeAsState()

        MaterialTheme(colors = darkThemeColors) {

            Scaffold(
                    topAppBar = {
                        topBar()
                    },
                    bodyContent = {
                        if (resident == null || resident?.loading == true) {
                            Loading()
                        } else {
                            Column() {
                                resident!!.residents.let {
                                    it.forEach { resident ->
                                        printResident(resident = resident)
                                    }
                                }

                            }
                        }
                    }
            )
        }
    }

    @Composable
    private fun printResident(resident: ResidentModel) {
        Column(
                modifier = Modifier.padding(0.dp, 10.dp)
        )
        {
            Row() {
                Column(
                        modifier = Modifier.padding(10.dp, 0.dp)
                ) {
                    Text(
                            text = "Name: " + resident.name,
                            color = darkThemeColors.onPrimary
                    )
                    Text(
                            text = "Rut: " + resident.rut,
                            color = darkThemeColors.onPrimary
                    )
                    Text(
                            text = "Email: " + resident.email,
                            color = darkThemeColors.onPrimary
                    )
                    Text(
                            text = "Phone: " + resident.phone.toString(),
                            color = darkThemeColors.onPrimary
                    )
                }

            }
        }
    }
}