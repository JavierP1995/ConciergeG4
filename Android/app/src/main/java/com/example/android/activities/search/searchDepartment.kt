package com.example.android.activities.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.graphics.ColorFilter
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Menu
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.R
import com.example.android.activities.display.displayDepartments
import com.example.android.activities.display.displayRecords
import com.example.android.activities.menu.menuRecord
import com.example.android.activities.ui.AndroidTheme
import com.example.android.adapter.DepartmentAdapter
import com.example.android.adapter.ResidentAdapter
import com.example.android.ui.utils.darkThemeColors
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class searchDepartment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                topBar()
            }
        }
    }

    @Preview
    @Composable
    private fun topBar() =
            MaterialTheme( colors = darkThemeColors) {
                TopAppBar(
                        title =
                        {
                            Text(text = "Search Filters",
                                    style = MaterialTheme.typography.h5)
                        },
                        backgroundColor = darkThemeColors.primary,
                        elevation = 0.dp
                )
                searchMenu()
            }

    @Composable
    private fun searchMenu() {
        var searchN = state { TextFieldValue("") }
        var searchR = state { TextFieldValue("") }
        var searchV = state { TextFieldValue("") }
        Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalGravity = Alignment.CenterHorizontally
        ) {
            Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalGravity = Alignment.CenterVertically
            ) {
                FilledTextField(
                        value = searchN.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { searchN.value = it },
                        label = { Text("Number: ") }
                )
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                Button(
                        text = { Text("Search By Number") },
                        backgroundColor = darkColorPalette().secondary,
                        modifier = Modifier.padding(0.dp, 10.dp),
                        onClick = {
                            val intent = Intent(this@searchDepartment,
                                    displayDepartments::class.java)
                            intent.putExtra("option", "byNumber")
                            intent.putExtra("search", searchN.value.text.toString())
                            startActivity(intent)
                        }
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalGravity = Alignment.CenterVertically
            ) {
                FilledTextField(
                        value = searchR.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { searchR.value = it },
                        label = { Text("Rut: ") }
                )
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                Button(
                        text = { Text("Search By Resident") },
                        backgroundColor = darkColorPalette().secondary,
                        modifier = Modifier.padding(0.dp, 10.dp),
                        onClick = {
                            val intent = Intent(this@searchDepartment,
                                    displayDepartments::class.java)
                            intent.putExtra("option", "byResident")
                            intent.putExtra("search", searchR.value.text.toString())
                            startActivity(intent)
                        }
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalGravity = Alignment.CenterVertically
            ) {
                FilledTextField(
                        value = searchV.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { searchV.value = it },
                        label = { Text("Rut: ") }
                )
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                Button(
                        text = { Text("Search By Visit") },
                        backgroundColor = darkColorPalette().secondary,
                        modifier = Modifier.padding(0.dp, 10.dp),
                        onClick = {
                            val intent = Intent(this@searchDepartment,
                                    displayDepartments::class.java)
                            intent.putExtra("option", "byVisit")
                            intent.putExtra("search", searchV.value.text.toString())
                            startActivity(intent)
                        }
                )
            }
        }
    }

}