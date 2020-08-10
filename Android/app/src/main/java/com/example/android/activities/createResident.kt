package com.example.android.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.activities.ui.AndroidTheme
import com.example.android.adapter.DepartmentAdapter
import com.example.android.adapter.ResidentAdapter
import com.example.android.menu.ResidentMenu
import com.example.android.model.ResidentModel
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class createResident : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTheme {
                // A surface container using the 'background' color from the theme
                MaterialTheme(colors = darkThemeColors) {
                    Reg()
                }
            }
        }
    }


    @Composable
    fun Reg() {
        var text = "Text"

        Scaffold( topAppBar = { TopAppBar(title = { Text(text = "Ingresar Residente") }) }

        ){
            Column(
                    modifier = Modifier.padding(25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalGravity = Alignment.CenterHorizontally
            ) {

                var rutVar = state{ TextFieldValue("") }
                FilledTextField(
                        value = rutVar.value,
                        onValueChange = { rutVar.value = it },
                        label = { Text("Rut") }
                )
                Divider()
                Spacer(modifier = Modifier.padding(top = 20.dp))

                var nameVar = state{ TextFieldValue("") }
                FilledTextField(
                            value = nameVar.value,
                            onValueChange = { nameVar.value = it },
                            label = { Text("Name") }
                )

                Divider()
                Spacer(modifier = Modifier.padding(top = 20.dp))

                var emailVar = state{ TextFieldValue("") }
                FilledTextField(
                        value = emailVar.value,
                        onValueChange = { emailVar.value = it},
                        label = { Text("Email") }
                )
                Divider()
                Spacer(modifier = Modifier.padding(top = 20.dp))

                var phoneVar = state{ TextFieldValue("") }
                FilledTextField(
                        value = phoneVar.value,
                        onValueChange = { phoneVar.value = it },
                        label = { Text("Phone") }
                )
                Divider()
                Spacer(modifier = Modifier.padding(top = 20.dp))

                var dNumberVar = state{ TextFieldValue("") }
                FilledTextField(
                        value = dNumberVar.value,
                        onValueChange = { dNumberVar.value = it },
                        label = { Text("Department Number") }
                )
                Divider()
                Spacer(modifier = Modifier.padding(top = 20.dp))


                Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {

                    Button(onClick = {
                        startActivity(
                                Intent(
                                        this@createResident,
                                        ResidentMenu::class.java
                                ))
                    }, text = { Text(text = "Cancelar") }
                    )

                    Button(modifier = Modifier.padding(start = 80.dp),
                            onClick = {
                                val rut = rutVar.component1().text
                                val name = nameVar.component1().text
                                val email= emailVar.component1().text
                                val phone = phoneVar.component1().text.toInt()
                                val dNumber = dNumberVar.component1().text.toInt()
                                lifecycleScope.launch {
                                    val rutine = withContext(Dispatchers.IO){
                                        ResidentAdapter.registerResident(
                                                rut,
                                                name,
                                                email,
                                                phone,
                                                dNumber
                                        )
                                    }
                                }
                            }, text = { Text(text = "Registrar") })
                }
            }

        }

    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultResidentPreview() {
        AndroidTheme {
            Reg()
        }
    }
}