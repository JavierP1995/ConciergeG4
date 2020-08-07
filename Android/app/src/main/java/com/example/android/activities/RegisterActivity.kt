package com.example.android.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            register()
        }

    }

    private fun callRegisterUser(name: TextFieldValue, email: TextFieldValue, password: TextFieldValue, passwordConfirmation:TextFieldValue){

        lifecycleScope.launch{
            withContext(Dispatchers.IO){
                registerUser(name.text, email.text, password.text, passwordConfirmation.text)
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
                   topAppBar = { topBar() },
                   bodyContent = {
                       
                       Image(
                               painter = ImagePainter(imageResource(id = R.drawable.register)),
                               modifier = Modifier.fillMaxWidth(),
                               alignment = Alignment.TopCenter
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
                                   onClick = { callRegisterUser(name.value, email.value, password.value, passwordConfirmation.value)} ,
                                   text = {Text(text = "Register")},
                                   modifier = Modifier.padding(20.dp),
                                   backgroundColor = darkColorPalette().secondary
                           )

                       }


                   }

           )
       }
   }

    @Composable
    fun topBar() {
        TopAppBar() {
            Text(
                    text = "Register",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.gravity(Alignment.CenterVertically)
            )

        }
    }


}
