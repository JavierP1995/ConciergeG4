package com.example.android.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
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
import com.example.android.adapter.RecordAdapter
import com.example.android.service.RecordService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterRecordActivity : AppCompatActivity() {

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
        var text = ""

        val visitRut = state{TextFieldValue("")}
        val departmentNumber= state{TextFieldValue("")}
        val residentName = state{TextFieldValue("")}
        val kinship = state{TextFieldValue("")}
        val comment = state{TextFieldValue("")}

        MaterialTheme(colors = darkColorPalette()) {
            Scaffold( topAppBar = { TopAppBar(title = { Text(text = "Agregar Registro")}) }

            ){
                Column(
                        modifier = Modifier.padding(25.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalGravity = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    FilledTextField(
                            value = visitRut.value,
                            activeColor = darkColorPalette().secondary,
                            onValueChange = { visitRut.value = it },
                            label = { Text("Rut Visitante*") }
                    )

                    Divider()
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    FilledTextField(
                            value = departmentNumber.value,
                            activeColor = darkColorPalette().secondary,
                            onValueChange = { departmentNumber.value = it},
                            label = { Text("Número Departamento*") }
                    )

                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    FilledTextField(
                            value = residentName.value,
                            activeColor = darkColorPalette().secondary,
                            onValueChange = { residentName.value = it},
                            label = { Text("Nombre Residente*") }
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    FilledTextField(
                            value = kinship.value ,
                            activeColor = darkColorPalette().secondary,
                            onValueChange = { kinship.value = it },
                            label = { Text("Parentezco*") }
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    FilledTextField(
                            value = comment.value,
                            activeColor = darkColorPalette().secondary,
                            onValueChange = { comment.value = it },
                            label = { Text("Comentario") }
                    )
                    Divider()
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                        Button(onClick = {/*Actividad aquí*/}, backgroundColor =
                        darkColorPalette().secondary,text = { Text(text = "Cancelar")})
                        Button(modifier = Modifier.padding(start = 80.dp),
                                backgroundColor = darkColorPalette().secondary, onClick =
                        {callRegisterActivity(visitRut.value, departmentNumber.value,
                        residentName.value, kinship.value, comment.value)},
                                text = { Text(text = "Registrar")})
                    }


                }

            }

        }


    }

    private fun callRegisterActivity(visitRut: TextFieldValue, departmentNumber: TextFieldValue, residentName: TextFieldValue, kinship: TextFieldValue, comment: TextFieldValue) {

        val visitRutAux = visitRut.text.toString()
        val residentNameAux = residentName.text.toString()
        val kinshipAux = kinship.text.toString()
        val commentAux = comment.text.toString()

        if(departmentNumber.text != ""){
            val apartmentNumber = departmentNumber.text.toInt()

            if (validateFields(visitRutAux, apartmentNumber, residentNameAux, kinshipAux, commentAux)){

                lifecycleScope.launch {
                    val register = withContext(Dispatchers.IO) {
                        RecordAdapter.createRecord(visitRutAux, apartmentNumber, residentNameAux, kinshipAux, commentAux)
                    }
                }
            }
        }else{
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, "The apartment number couldn't be null", duration)
            toast.show()
        }

    }

    private fun validateFields(visitRut: String, departmentNumber: Int, residentName: String, kinship: String, comment: String?): Boolean {

        if(visitRut == "" || residentName == "" || departmentNumber == null || kinship == ""){
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, "You must specify all the fields !", duration)
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