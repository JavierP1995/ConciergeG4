package com.example.android.activities.menu

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.*
import androidx.ui.foundation.Border
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.RectangleShape
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.Density
import androidx.ui.unit.dp
import com.example.android.R
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                menu()
        }
    }


    @Preview
    @Composable
    private fun menu() {

        MaterialTheme(colors = darkThemeColors){

            Scaffold(
                    bodyContent = {

                        Image(
                                painter = ImagePainter(imageResource(id = R.drawable.mainmenu)),
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

                        ){
                            Button(
                                    text = { Text("Records", color = darkThemeColors.onPrimary,
                                            style = MaterialTheme.typography.h6) },
                                    backgroundColor = Color.Transparent,
                                    border = Border(5.dp, darkThemeColors.secondary),
                                    modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        startActivity(
                                                Intent(this@MainActivity,
                                                        menuRecord::class.java
                                                )
                                        )
                                    }
                            )
                            Button(
                                    text = { Text("Residents", color = darkThemeColors.onPrimary,
                                            style = MaterialTheme.typography.h6
                                    )},
                                    backgroundColor = Color.Transparent,
                                    border = Border(5.dp, darkThemeColors.secondary),
                                    modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        startActivity(
                                                Intent(this@MainActivity,
                                                        menuResident::class.java
                                                )
                                        )
                                    }
                            )


                            Button(
                                    text = { Text("Departments", color = darkThemeColors.onPrimary,
                                            style = MaterialTheme.typography.h6) },
                                    backgroundColor = Color.Transparent,
                                    border = Border(5.dp, darkThemeColors.secondary),
                                    modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        startActivity(
                                                Intent(this@MainActivity,
                                                        menuDepartment::class.java
                                                )
                                        )
                                    }
                            )
                            Button(
                                    text = { Text("Visits", color = darkThemeColors.onPrimary,
                                            style = MaterialTheme.typography.h6) },
                                    backgroundColor = Color.Transparent,
                                    border = Border(5.dp, darkThemeColors.secondary),
                                    modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        startActivity(
                                                Intent(this@MainActivity,
                                                        menuVisit::class.java
                                                )
                                        )
                                    }
                            )


                        }
                    })
        }

    }





}






