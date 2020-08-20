package com.example.android.activities.save

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
import com.example.android.adapter.RecordAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveRecord : AppCompatActivity() {

    companion object{
        var token : String = ""

        fun setLoginData(authToken : String){
            this.token = authToken
            Log.v("TOKEN", this.token)
        }
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            showForm()
        }

    }

    /**
     *
     */
    @Preview
    @Composable
    fun showForm() {

        val visitRut = state{TextFieldValue("")}
        val departmentNumber= state{TextFieldValue("")}
        val residentName = state{TextFieldValue("")}
        val kinship = state{TextFieldValue("")}
        val comment = state{TextFieldValue("")}

        MaterialTheme(colors = darkColorPalette()) {
            Scaffold(
                    bodyContent = {

                        Image(
                                painter = ImagePainter(imageResource(id = R.drawable.menurecords)),
                                alignment = Alignment.TopCenter
                        )
                        Image(
                                painter = ImagePainter(imageResource(id = R.drawable.conciergewallpaper)),
                                alignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                        )

                        Column(
                                modifier = Modifier.padding(55.dp).fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalGravity = Alignment.CenterHorizontally
                        ) {

                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            FilledTextField(
                                    value = visitRut.value,
                                    activeColor = darkColorPalette().secondary,
                                    shape = RoundedCornerShape(10.dp),
                                    onValueChange = { visitRut.value = it },
                                    label = { Text("Visit Rut*") }
                            )

                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            FilledTextField(
                                    value = departmentNumber.value,
                                    activeColor = darkColorPalette().secondary,
                                    shape = RoundedCornerShape(10.dp),
                                    onValueChange = { departmentNumber.value = it},
                                    label = { Text("Apartment Number*") }
                            )

                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            FilledTextField(
                                    value = residentName.value,
                                    activeColor = darkColorPalette().secondary,
                                    shape = RoundedCornerShape(10.dp),
                                    onValueChange = { residentName.value = it},
                                    label = { Text("Resident Name*") }
                            )
                            Spacer(modifier = Modifier.padding(top = 20.dp))

                            FilledTextField(
                                    value = kinship.value ,
                                    activeColor = darkColorPalette().secondary,
                                    shape = RoundedCornerShape(10.dp),
                                    onValueChange = { kinship.value = it },
                                    label = { Text("Kinship*") }
                            )
                            Spacer(modifier = Modifier.padding(top = 20.dp))

                            FilledTextField(
                                    value = comment.value,
                                    activeColor = darkColorPalette().secondary,
                                    shape = RoundedCornerShape(10.dp),
                                    onValueChange = { comment.value = it },
                                    label = { Text("Comment") }
                            )
                            Spacer(modifier = Modifier.padding(top = 20.dp))

                            Row(modifier = Modifier.padding(top = 20.dp, end = 10.dp)) {
                                Button(
                                        onClick = {/*Actividad aquí*/},
                                        backgroundColor = Color.Transparent,
                                        shape = RoundedCornerShape(10.dp),
                                        border = Border(5.dp, darkColorPalette().secondary),
                                        text = { Text(text = "Cancel", style = MaterialTheme.typography.h6)})
                                Button(
                                        modifier = Modifier.padding(start = 40.dp),
                                        backgroundColor = Color.Transparent,
                                        shape = RoundedCornerShape(10.dp),
                                        border = Border(5.dp, darkColorPalette().secondary),
                                        onClick = {
                                                    callRegisterActivity(visitRut.value,
                                                            departmentNumber.value,
                                                            residentName.value, kinship.value,
                                                            comment.value)},
                                        text = { Text(text = "Register", style = MaterialTheme.typography.h6)}
                                )
                            }


                        }

                    }

            )

        }


    }

    /**
     *
     */
    private fun callRegisterActivity(visitRut: TextFieldValue, departmentNumber: TextFieldValue, residentName: TextFieldValue, kinship: TextFieldValue, comment: TextFieldValue) {

        val visitRutAux = visitRut.text
        val residentNameAux = residentName.text
        val kinshipAux = kinship.text
        val commentAux = comment.text

        if(departmentNumber.text != ""){
            val apartmentNumber = departmentNumber.text.toInt()

            if (validateFields(visitRutAux, apartmentNumber, residentNameAux, kinshipAux, commentAux)){

                lifecycleScope.launch {
                    val register = withContext(Dispatchers.IO) {
                        RecordAdapter.createRecord(token, visitRutAux, apartmentNumber, residentNameAux, kinshipAux, commentAux)
                    }
                }
                val duration = Toast.LENGTH_SHORT
                val toast =
                        Toast.makeText(applicationContext, "Insertion Succesfully !", duration)
                toast.show()
            }
        }else{
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, "The apartment number couldn't be null", duration)
            toast.show()
        }

    }

    /**
     *
     */
    private fun validateFields(visitRut: String, departmentNumber: Int, residentName: String, kinship: String, comment: String?): Boolean {

        if(visitRut == "" || residentName == "" || departmentNumber == null || kinship == ""){
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, "You must specify all the fields !", duration)
            toast.show()
            return false
        }

        return true
    }
}