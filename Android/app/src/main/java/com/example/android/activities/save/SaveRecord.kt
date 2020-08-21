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
import androidx.ui.input.KeyboardType
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

/**
 * Class SaveRecord, activity that save a record in data base.
 */
class SaveRecord : AppCompatActivity() {


    var error= false
    lateinit var message: String

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
                                        onClick = {finish()},
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
        lateinit var displayMessage: String

        if(departmentNumber.text != ""){
            val apartmentNumber = departmentNumber.text.toInt()

            if (validateFields(visitRutAux, apartmentNumber, residentNameAux, kinshipAux, commentAux)){

                lifecycleScope.launch {
                    val register = withContext(Dispatchers.IO) {
                        val response = RecordAdapter.createRecord(token, visitRutAux, apartmentNumber, residentNameAux, kinshipAux, commentAux)

                        message = response?.message.toString()


                        when (message) {
                            "The provided visit name it's not exists !" -> {
                                error = true
                                displayMessage = "The visitant it's not registered !"
                                Log.v("RESPONSE MESSAGE: ", message)
                            }
                            "The provided number it's not exists !" -> {
                                error = true
                                displayMessage = "The department number entered does not exist !!"
                                Log.v("RESPONSE MESSAGE: ", message)
                            }
                            "The visitant isn't admitted" -> {
                                error = true
                                displayMessage = "The visitant it's not admitted !"
                                Log.v("RESPONSE MESSAGE: ", message)
                            }
                            "The visitant is not registered !" -> {
                                error = true
                                displayMessage = "The visitant it's not registered !"
                                Log.v("RESPONSE MESSAGE: ", message)
                            }
                            else -> error = false
                        }

                    }
                    if(error){
                        showMessage(message = displayMessage)
                    }else{
                        showMessage(message = "Insertion Successfully !!!")
                    }
                }
            }
        }else{
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, "The apartment number couldn't be null", duration)
            toast.show()
        }

    }

    /**
     * Method that validates the parameters of the object being entered
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

    /**
     * This function allows us to display a toast message
     */
    private fun showMessage(message: String?) {

        val duration = Toast.LENGTH_SHORT
        val toast =
                Toast.makeText(applicationContext, message, duration)
        toast.show()

    }


}