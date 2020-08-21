package com.example.android.activities.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.*
import androidx.ui.foundation.Border
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.darkColorPalette
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.R
import com.example.android.activities.save.SaveRecord
import com.example.android.adapter.AuthAdapter
import com.example.android.adapter.RecordAdapter
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.*

/**
 * Main menu visualization, with access to the specified entity's
 */
class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    /**
     * Global variable used to receive and send the token in the methods.
     */
    companion object {
        var token: String = ""

        /**
         * Method to change the token value
         */
        fun setLoginData(authToken: String) {
            this.token = authToken
            Log.v("TOKEN", this.token)
        }
    }

    /**
     * Method to initialize the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            menu()
        }
    }


    /**
     * Visual display and buttons to access the activity's for the specified tasks, a variable is
     * send in the intent if needed
     */
    @Preview
    @Composable
    private fun menu() {

        loginSuccessfullyMessage()
        MaterialTheme(colors = darkThemeColors) {

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

                        ) {
                            Button(
                                    text = {
                                        Text("Records", color = darkThemeColors.onPrimary,
                                                style = MaterialTheme.typography.h6)
                                    },
                                    backgroundColor = Color.Transparent,
                                    border = Border(5.dp, darkThemeColors.secondary),
                                    modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        MenuRecord.setLoginData(token)
                                        startActivity(
                                                Intent(this@MainActivity,
                                                        MenuRecord::class.java
                                                )
                                        )
                                    }
                            )
                            Button(
                                    text = {
                                        Text("Residents", color = darkThemeColors.onPrimary,
                                                style = MaterialTheme.typography.h6
                                        )
                                    },
                                    backgroundColor = Color.Transparent,
                                    border = Border(5.dp, darkThemeColors.secondary),
                                    modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        MenuResident.setLoginData(token)
                                        startActivity(
                                                Intent(this@MainActivity,
                                                        MenuResident::class.java
                                                )
                                        )
                                    }
                            )


                            Button(
                                    text = {
                                        Text("Departments", color = darkThemeColors.onPrimary,
                                                style = MaterialTheme.typography.h6)
                                    },
                                    backgroundColor = Color.Transparent,
                                    border = Border(5.dp, darkThemeColors.secondary),
                                    modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        MenuDepartment.setLoginData(token)
                                        startActivity(
                                                Intent(this@MainActivity,
                                                        MenuDepartment::class.java
                                                )
                                        )
                                    }
                            )
                            Button(
                                    text = {
                                        Text("Visits", color = darkThemeColors.onPrimary,
                                                style = MaterialTheme.typography.h6)
                                    },
                                    backgroundColor = Color.Transparent,
                                    border = Border(5.dp, darkThemeColors.secondary),
                                    modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    onClick = {
                                        MenuVisit.setLoginData(token)
                                        startActivity(
                                                Intent(this@MainActivity,
                                                        MenuVisit::class.java
                                                )
                                        )
                                    }
                            )

                            Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                                Button(
                                        text = {
                                            Text("Logout", color = darkThemeColors.onPrimary,
                                                    style = MaterialTheme.typography.h6)
                                        },
                                        backgroundColor = Color.Transparent,
                                        border = Border(5.dp, darkThemeColors.secondary),
                                        modifier = Modifier.size(300.dp, 100.dp).padding(0.dp, 10.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        onClick = {
                                            doLogout()
                                        }
                                )
                            }

                        }
                    })
        }

    }


    /**
     * This function allows you to log out
     */
    private fun doLogout(){

        var message: String

        lifecycleScope.launch {
            val register = withContext(Dispatchers.IO) {
                val response = AuthAdapter.logoutUser(token)

                message = response?.get(0).toString()

            }
        }
    }

    /**
     * Display a message if the login was correct
     */
    private fun loginSuccessfullyMessage() {
        val duration = Toast.LENGTH_SHORT
        val toast =
                Toast.makeText(applicationContext, "Login Successfully !", duration)
        toast.show()
    }

}






