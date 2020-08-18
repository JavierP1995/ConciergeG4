package com.example.android.activities.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.activities.display.DisplayVisits
import com.example.android.ui.utils.darkThemeColors

class SearchVisit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                topBar()
            }
        }
    }

    companion object{
        var token : String = ""

        fun setLoginData(authToken : String){
            this.token = authToken
            Log.v("TOKEN", this.token)
        }
    }

    @Preview
    @Composable
    private fun topBar() =
            MaterialTheme( colors = darkThemeColors) {
                TopAppBar(
                        title =
                        {
                            Text(text = "Visit Search",
                                    style = MaterialTheme.typography.h5)
                        },
                        backgroundColor = darkThemeColors.primary,
                        elevation = 0.dp
                )
                searchMenu()
            }

    @Composable
    private fun searchMenu() {
        val searchR = state { TextFieldValue("") }
        val searchRe = state { TextFieldValue("") }
        val searchD = state { TextFieldValue("") }
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
                        value = searchR.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { searchR.value = it },
                        label = { Text("Rut: ") }
                )
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                Button(
                        text = { Text("Search By Rut") },
                        backgroundColor = darkColorPalette().secondary,
                        modifier = Modifier.padding(0.dp, 10.dp),
                        onClick = {
                            val intent = Intent(this@SearchVisit,
                                    DisplayVisits::class.java)
                            intent.putExtra("option", "byRut")
                            intent.putExtra("search", searchR.value.text)
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
                        value = searchRe.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { searchRe.value = it },
                        label = { Text("Rut: ") }
                )
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                Button(
                        text = { Text("Search By Resident") },
                        backgroundColor = darkColorPalette().secondary,
                        modifier = Modifier.padding(0.dp, 10.dp),
                        onClick = {
                            val intent = Intent(this@SearchVisit,
                                    DisplayVisits::class.java)
                            intent.putExtra("option", "byResident")
                            intent.putExtra("search", searchRe.value.text)
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
                        value = searchD.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { searchD.value = it },
                        label = { Text("Rut: ") }
                )
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                Button(
                        text = { Text("Search By Department") },
                        backgroundColor = darkColorPalette().secondary,
                        modifier = Modifier.padding(0.dp, 10.dp),
                        onClick = {
                            val intent = Intent(this@SearchVisit,
                                    DisplayVisits::class.java)
                            intent.putExtra("Authorization", SearchVisit.token)
                            intent.putExtra("option", "byDepartment")
                            intent.putExtra("search", searchD.value.text)
                            startActivity(intent)
                        }
                )
            }
        }
    }

}