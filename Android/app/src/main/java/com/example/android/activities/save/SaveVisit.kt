package com.example.android.activities.save

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.ColorFilter
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Menu
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.R
import com.example.android.activities.ui.AndroidTheme
import com.example.android.activities.menu.MenuRecord
import com.example.android.adapter.VisitAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Class SaveVisit, activity that save a visit in data base.
 */
class SaveVisit : AppCompatActivity() {

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
        super.onCreate(savedInstanceState)
        setContent {
           showForm()
        }

    }

    /**
     * The method used shows the fields required to insert a record in the database.
     */
    @Preview
    @Composable
    fun showForm() {

        val rut = state { TextFieldValue("") }
        val name = state { TextFieldValue("") }
        val admitted = state { TextFieldValue("") }

        MaterialTheme(colors = darkColorPalette()) {
            Scaffold(

                    bodyContent = {

                        Image(
                                painter = ImagePainter(imageResource(id = R.drawable.menuvisits)),
                                alignment = Alignment.TopCenter
                        )
                        Image(
                                painter = ImagePainter(imageResource(id = R.drawable.conciergewallpaper)),
                                alignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                        )

                        Column(
                                modifier = Modifier.fillMaxSize().padding(55.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalGravity = Alignment.CenterHorizontally
                        ) {

                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            FilledTextField(
                                    value = rut.value,
                                    activeColor = darkColorPalette().secondary,
                                    shape = RoundedCornerShape(10.dp),
                                    onValueChange = { rut.value = it },
                                    label = { Text("Rut *") }
                            )

                            Spacer(modifier = Modifier.padding(top = 20.dp))

                            FilledTextField(
                                    value = name.value,
                                    activeColor = darkColorPalette().secondary,
                                    shape = RoundedCornerShape(10.dp),
                                    onValueChange = { name.value = it },
                                    label = { Text("Name *") }
                            )

                            Spacer(modifier = Modifier.padding(top = 20.dp))

                            FilledTextField(
                                    value = admitted.value,
                                    activeColor = darkColorPalette().secondary,
                                    shape = RoundedCornerShape(10.dp),
                                    onValueChange = { admitted.value = it },
                                    label = { Text("Admitted *") }
                            )

                            Spacer(modifier = Modifier.padding(top = 20.dp))

                            Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                                Button(
                                        onClick = {
                                            startActivity(Intent(this@SaveVisit,
                                            MenuRecord::class.java)
                                            )},
                                        backgroundColor = Color.Transparent,
                                        shape = RoundedCornerShape(10.dp),
                                        border = Border(5.dp, darkColorPalette().secondary),
                                        text = { Text(text = "Cancel", style = MaterialTheme.typography.h6)})
                                Button(onClick = { callRegister(rut.value, name.value,
                                                admitted.value)
                                        },
                                        modifier = Modifier.padding(start = 25.dp),
                                        backgroundColor = Color.Transparent,
                                        shape = RoundedCornerShape(10.dp),
                                        border = Border(5.dp, darkColorPalette().secondary),
                                        text = { Text(text = "Register",
                                                style = MaterialTheme.typography.h6)}
                                )
                            }
                        }

                    }

            )
        }
    }

    /**
     * Method used to send the attributes of the object to save in the database.
     */
    private fun callRegister(rut: TextFieldValue, name: TextFieldValue, admitted: TextFieldValue) {
        val rut = rut.text
        val name = name.text
        val admitted = admitted.text

        if (validateFields(rut, name, admitted)) {
            lifecycleScope.launch{
                val register = withContext(Dispatchers.IO)
                {
                    VisitAdapter.createVisit(token , rut, name, admitted)
                }
            }
            val duration = Toast.LENGTH_SHORT
            val toast =
                    Toast.makeText(applicationContext, "Insertion Succesfully !", duration)
            toast.show()
        }

    }

    /**
     * Method that validates the parameters of the object being entered
     */
    private fun validateFields(
        rut: String,
        name: String,
        admitted: String
    ): Boolean {
        if (rut == "" || name == "" || admitted == "") {
            val duration = Toast.LENGTH_SHORT
            val toast =
                Toast.makeText(applicationContext, "You must specify all the fields!", duration)
            toast.show()
            return false
        }
        return true
    }

}