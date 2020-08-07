package com.example.android.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.activities.ui.AndroidTheme
import com.example.android.adapter.ResidentAdapter

class RegisterResidentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Reg()
                }
            }
        }
    }
}

@Composable
fun Reg() {
    var text = "Text"

    Scaffold( topAppBar = { TopAppBar(title = { Text(text = "Ingresar Residente")}) }

    ){
        Column(
                modifier = Modifier.padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalGravity = Alignment.CenterHorizontally
        ) {
            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Rut") }
            )
            Divider()
            Spacer(modifier = Modifier.padding(top = 20.dp))

            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Nombre") }
            )
            Divider()
            Spacer(modifier = Modifier.padding(top = 20.dp))

            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Email") }
            )
            Divider()
            Spacer(modifier = Modifier.padding(top = 20.dp))

            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Phone") }
            )
            Divider()
            Spacer(modifier = Modifier.padding(top = 20.dp))

            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Department Number") }
            )
            Divider()
            Spacer(modifier = Modifier.padding(top = 20.dp))

            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                Button(onClick = {/*Actividad aquí*/}, text = { Text(text = "Cancelar")})
                Button(modifier = Modifier.padding(start = 80.dp),
                        onClick = {
                            //ResidentAdapter.registerResident()
                        }, text = { Text(text = "Registrar")})
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