package com.example.android.activities

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
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

class RegisterRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    var text = "Text"

    Scaffold( topAppBar = { TopAppBar(title = { Text(text = "Agregar Registro")}) }

    ){
        Column(
                modifier = Modifier.padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalGravity = Alignment.CenterHorizontally
        ) {
            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Rut Visitante") }
            )

            Divider()
            Spacer(modifier = Modifier.padding(top = 20.dp))
            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Número Departamento") }
            )

            Divider()
            Spacer(modifier = Modifier.padding(top = 20.dp))
            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Nombre Residente") }
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Bloque") }
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            FilledTextField(
                    value = text,
                    onValueChange = { /* */ },
                    label = { Text("Parentezco") }
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            TimePicker()
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                Button(onClick = {/*Actividad aquí*/}, text = { Text(text = "Cancelar")})
                Button(modifier = Modifier.padding(start = 80.dp), onClick = {/*Actividad aquí*/}, text = { Text(text = "Registrar")})
            }
        }

    }

}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidTheme {
        Greeting()
    }
}