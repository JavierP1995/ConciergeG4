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
import com.example.android.adapter.ResidentAdapter
import com.example.android.activities.menu.MenuRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveResident : AppCompatActivity() {

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
        val email = state { TextFieldValue("") }
        val phone= state { TextFieldValue("") }
        val department = state { TextFieldValue("") }

        MaterialTheme(colors = darkColorPalette()) {
            Scaffold(topAppBar = {
                TopAppBar(title = { Text(text = "Add Resident") },
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
                        value = email.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { email.value = it },
                        label = { Text("Email*") }
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    FilledTextField(
                        value = phone.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { phone.value = it },
                        label = { Text("Phone*") }
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    FilledTextField(
                        value = department.value,
                        activeColor = darkColorPalette().secondary,
                        onValueChange = { department.value = it },
                        label = { Text("Department*") }
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@SaveResident,
                                    MenuRecord::class.java
                                ))
                        }, backgroundColor =
                        darkColorPalette().secondary, text = { Text(text = "Cancel") })
                        Button(modifier = Modifier.padding(start = 40.dp),
                            backgroundColor = darkColorPalette().secondary, onClick =
                            {
                                callRegister(
                                    rut.value, name.value, email.value, phone.value,
                                    department.value
                                )
                            },
                            text = { Text(text = "Register") })
                    }
                }
            }
        }
    }

    private fun callRegister(rut: TextFieldValue, name: TextFieldValue, email: TextFieldValue,
                             phone: TextFieldValue, department: TextFieldValue) {
        val rut = rut.text
        val name = name.text
        val email = email.text
        val phone = phone.text.toInt()
        val department = department.text.toInt()


        if (validateFields(rut, name, email, phone, department)) {
            lifecycleScope.launch{
                val register = withContext(Dispatchers.IO)
                {
                    ResidentAdapter.createResident(token,
                        rut, name, email, phone, department)
                }
            }
        }

    }

    private fun validateFields(
        rut: String,
        name: String,
        email: String,
        phone: Int,
        department: Int?
    ): Boolean {
        if (rut == "" || name == "" || email == "" || phone == null || department == null) {
            val duration = Toast.LENGTH_SHORT
            val toast =
                Toast.makeText(applicationContext, "You must specify all the fields!", duration)
            toast.show()
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