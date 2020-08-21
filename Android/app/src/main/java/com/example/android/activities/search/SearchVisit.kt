package com.example.android.activities.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.paint
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.R
import com.example.android.activities.display.DisplayVisits
import com.example.android.ui.utils.darkThemeColors

/**
 * Activity to use search visit by number of department, rut of resident or visit
 */
class SearchVisit : AppCompatActivity() {

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
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                searchMenu()
            }
        }
    }

    /**
     * Method used to display menu with options to search visit
     */
    @Preview
    @Composable
    private fun searchMenu() {
        val searchV = state { TextFieldValue("") }
        val searchR = state { TextFieldValue("") }
        val searchD = state { TextFieldValue("") }

        MaterialTheme(colors = darkThemeColors) {

            Scaffold(
                    bodyContent = {

                        Image(
                                painter = ImagePainter(imageResource(id = R.drawable.appbarsearch)),
                                alignment = Alignment.TopCenter
                        )
                        Image(
                                painter = ImagePainter(imageResource(id = R.drawable.conciergewallpaper)),
                                alignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                        )

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
                                        value = searchD.value,
                                        activeColor = darkColorPalette().secondary,
                                        shape = RoundedCornerShape(10.dp),
                                        onValueChange = { searchD.value = it },
                                        label = { Text("Search by number of department ") }
                                )
                                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                                Button(
                                        text = { Text("Search By Department") },
                                        backgroundColor = darkColorPalette().secondary,
                                        modifier = Modifier.paint(painter = ImagePainter(imageResource(id = R.drawable.iconsearch)),
                                                sizeToIntrinsics = false),
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
                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalGravity = Alignment.CenterVertically
                            ) {
                                FilledTextField(
                                        value = searchV.value,
                                        activeColor = darkColorPalette().secondary,
                                        shape = RoundedCornerShape(10.dp),
                                        onValueChange = { searchV.value = it },
                                        label = { Text("Search by rut of visit ") }
                                )
                                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                                Button(
                                        text = { Text("Search By Rut") },
                                        backgroundColor = darkColorPalette().secondary,
                                        modifier = Modifier.paint(painter = ImagePainter(imageResource(id = R.drawable.iconsearch)),
                                                sizeToIntrinsics = false),
                                        onClick = {
                                            val intent = Intent(this@SearchVisit,
                                                    DisplayVisits::class.java)
                                            intent.putExtra("option", "byRut")
                                            intent.putExtra("search", searchV.value.text)
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
                                        shape = RoundedCornerShape(10.dp),
                                        onValueChange = { searchR.value = it },
                                        label = { Text("Search by rut of resident ") }
                                )
                                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                                Button(
                                        text = { Text("Search by rut of resident") },
                                        backgroundColor = darkColorPalette().secondary,
                                        modifier = Modifier.paint(painter = ImagePainter(imageResource(id = R.drawable.iconsearch)),
                                                sizeToIntrinsics = false),
                                        onClick = {
                                            val intent = Intent(this@SearchVisit,
                                                    DisplayVisits::class.java)
                                            intent.putExtra("option", "byResident")
                                            intent.putExtra("search", searchR.value.text)
                                            startActivity(intent)
                                        }
                                )
                            }
                        }

            })

        }



    }

}