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
import androidx.ui.foundation.Border
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
import androidx.ui.graphics.Color
import androidx.ui.graphics.painter.ImagePainter
import androidx.ui.input.KeyboardType
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.R
import com.example.android.adapter.AuthAdapter.registerUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegisterActivity : AppCompatActivity() {

    var error = false
    lateinit var message : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            register()
        }

    }

    private fun callRegisterUser(name: TextFieldValue, email: TextFieldValue, password: TextFieldValue, passwordConfirmation:TextFieldValue){

        if(validateFields(name, email, password, passwordConfirmation)){

            lifecycleScope.launch{
                withContext(Dispatchers.IO){
                    val response = registerUser(name.text, email.text, password.text, passwordConfirmation.text)
                    message = response
                    if (message == "Precondition Failed"){
                        error = true
                    }
                }
                if(error){
                    showMessage(message = "Validation Error, try again!")
                }
                else{
                    showMessage("Created Successfully!")
                    finish()
                }
            }

        }


    }

   @Preview
   @Composable
   fun register() {

       val name = state {
           TextFieldValue()
       }
       val email = state {
           TextFieldValue()
       }
       val password = state {
           TextFieldValue()
       }
       val passwordConfirmation = state {
           TextFieldValue()
       }

       MaterialTheme(colors = darkColorPalette() ) {

           Scaffold(

                   bodyContent = {

                       Image(
                               painter = ImagePainter(imageResource(id = R.drawable.registerbar)),
                               modifier = Modifier.fillMaxWidth(),
                               alignment = Alignment.TopCenter
                       )

                       Image(
                               painter = ImagePainter(imageResource(id = R.drawable.conciergewallpaper)),
                               modifier = Modifier.fillMaxSize(),
                               alignment = Alignment.Center
                       )
                       Column(
                               modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                               horizontalGravity = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.Center
                       )
                       {

                           FilledTextField(
                                   value = name.value,
                                   onValueChange = {name.value = it },
                                   activeColor = darkColorPalette().secondary,
                                   keyboardType = KeyboardType.Text,
                                   label = { Text("Name") }
                           )

                           FilledTextField(
                                   value = email.value,
                                   onValueChange = { email.value = it },
                                   activeColor = darkColorPalette().secondary,
                                   keyboardType = KeyboardType.Email,
                                   label = { Text("Email") }
                           )

                           FilledTextField(
                                   value = password.value,
                                   onValueChange = { password.value = it },
                                   activeColor = darkColorPalette().secondary,
                                   label = { Text("Password") },
                                   keyboardType = KeyboardType.Password,
                                   visualTransformation = PasswordVisualTransformation()
                           )
                           FilledTextField(
                                   value = passwordConfirmation.value,
                                   onValueChange = { passwordConfirmation.value = it },
                                   activeColor = darkColorPalette().secondary,
                                   label = { Text("Confirmed Password") },
                                   keyboardType = KeyboardType.Password,
                                   visualTransformation = PasswordVisualTransformation()
                           )

                           Button(
                                   onClick = { callRegisterUser(name.value, email.value,
                                           password.value, passwordConfirmation.value) },
                                   text = {Text(text = "Register")},
                                   modifier = Modifier.padding(20.dp),
                                   backgroundColor = Color.Transparent,
                                   border = Border(2.dp, darkColorPalette().secondary)
                           )

                       }


                   }

           )
       }
   }

    /**
     * Method that validates the parameter of the object being entered
     */
    private fun validateFields(name: TextFieldValue, email: TextFieldValue, password: TextFieldValue,
                               passwordConfirmation: TextFieldValue) : Boolean{

        if(name.text == "" || email.text == "" || password.text == "" || passwordConfirmation.text == ""){
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, "You must specify all the fields!", duration)
            toast.show()
            return false
        }

        return true
    }

    private fun showMessage(message: String) {

        val duration = Toast.LENGTH_SHORT
        val toast =
                Toast.makeText(applicationContext, message, duration)
        toast.show()

    }



}
