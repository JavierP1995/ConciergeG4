package com.example.android.activities.save

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
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
import com.example.android.activities.ui.AndroidTheme
import com.example.android.activities.menu.MenuRecord
import com.example.android.adapter.VisitAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveVisit : AppCompatActivity() {

    var error= false
    lateinit var message: String

    companion object{
        var token : String = ""

        fun setLoginData(authToken : String){
            this.token = authToken
            Log.v("TOKEN", this.token)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    showForm()
                }
            }
        }

    }

    @Composable
    fun showForm() {

        val rut = state { TextFieldValue("") }
        val name = state { TextFieldValue("") }
        val admitted = state { TextFieldValue("") }

        MaterialTheme(colors = darkColorPalette()) {
            Scaffold(topAppBar = {
                TopAppBar(title = { Text(text = "Add Visit") },
                    navigationIcon = {
                        IconButton(onClick = {/*Activity*/ }) {
                            Icon(Icons.Filled.Menu)
                        }
                    })
            }

            ) {
                Image(
                    asset = imageResource(id = R.drawable.register_icon),
                    alignment = Alignment.Center,
                    modifier = Modifier.padding(start = 25.dp, bottom = 40.dp),
                    colorFilter = ColorFilter.tint(Color(red = 25, blue = 25, green = 25)),
                    contentScale = ContentScale.Fit
                )

                Column(
                    modifier = Modifier.padding(55.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalGravity = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    FilledTextField(
                        value = rut.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { rut.value = it },
                        label = { Text("Rut*") }
                    )

                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    FilledTextField(
                        value = name.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { name.value = it },
                        label = { Text("Name*") }
                    )

                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    FilledTextField(
                        value = admitted.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { admitted.value = it },
                        label = { Text("Admitted*") }
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                        Button(onClick = {finish()},
                            backgroundColor = darkColorPalette().secondary, text = { Text(text = "Cancel") })
                        Button(modifier = Modifier.padding(start = 40.dp),
                            backgroundColor = darkColorPalette().secondary, onClick =
                            {
                                callRegister(
                                    rut.value, name.value, admitted.value
                                )
                            },
                            text = { Text(text = "Register") })
                    }
                }
            }
        }
    }

    /**
     * This function allows us to call to the createVisit adapter method.
     */
    private fun callRegister(rut: TextFieldValue, name: TextFieldValue, admitted: TextFieldValue) {
        val rut = rut.text
        val name = name.text
        val admitted = admitted.text
        lateinit var displayMessage : String

        if (validateFields(rut, name, admitted)) {
            lifecycleScope.launch{
                val register = withContext(Dispatchers.IO)
                {
                    val response = VisitAdapter.createVisit(token , rut, name, admitted)
                    message = response?.message.toString()

                    if(message == "Created Successfully"){
                        error = false
                        displayMessage = "Insertion Successfully !!"
                    }else{
                        error = true
                    }

                }
                if(error){
                    showMessage(message = "An error occurred, please try again !")
                }else{
                    showMessage(message = displayMessage)
                }
            }
        }

    }

    /**
     * This function allows us to display a toast message
     */
    private fun showMessage(message
                            : String?) {

        val duration = Toast.LENGTH_SHORT
        val toast =
                Toast.makeText(applicationContext, message, duration)
        toast.show()

    }

    /**
     * This function validate if the fields are empty.
     */
    private fun validateFields(
        rut: String,
        name: String,
        admitted: String
    ): Boolean {
        if (rut == "" || name == "" || admitted == "") {
            showMessage(message = "You must specify all the fields!")
            return false
        }
        return true
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaterialTheme() {
            showForm()
        }
    }


}