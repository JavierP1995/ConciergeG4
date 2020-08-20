package com.example.android.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.R
import com.example.android.activities.display.DisplayDepartments
import com.example.android.activities.menu.MainActivity
import com.example.android.activities.menu.MenuDepartment
import com.example.android.adapter.AuthAdapter.loginUser
import com.example.android.reponse.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    var errorValidation = false
    var errorCredentials = false
    lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            login()
        }

    }

    private fun callLogin(email : TextFieldValue, password : TextFieldValue) {

        if (validateFields(email, password)){
            lifecycleScope.launch(){
                withContext(Dispatchers.IO){
                    var response = (loginUser(email.text,password.text))
                    val auth = (response?.get(1))
                    message = (response?.get(0).toString())

                    if(message == "Unauthorized") {
                        errorCredentials = true
                    }else if (message == "Precondition Failed"){
                        errorValidation = true
                    }else{

                        if (auth != null) {
                            Log.v("TOKEN", auth)

                            MainActivity.setLoginData(auth)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }

                    }

                }
                when {
                    errorCredentials -> {
                        showMessage(message = "Invalid credentials, try again !")
                    }
                    errorValidation -> {
                        showMessage(message = "Validation error, check your inputs please !!")
                    }
                    else -> {
                        finish()
                    }
                }
            }
        }

    }

    @Preview
    @Composable
    fun login() {

        val email = state {
            TextFieldValue()
        }
        val password = state {
            TextFieldValue()
        }

        MaterialTheme(colors = darkColorPalette() ) {

            Scaffold(

                    bodyContent = {

                        Image(
                                painter = ImagePainter(imageResource(id = R.drawable.concierge)),
                                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                                alignment = Alignment.Center
                        )
                        Column(
                                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                                horizontalGravity = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                        )
                        {
                            Text(
                                    text = "Sign In",
                                    modifier = Modifier.fillMaxWidth().padding(0.dp, 15.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h4

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

                            Button(
                                    onClick = {callLogin(email.value,password.value)},
                                    text = {Text(text = "Login")},
                                    modifier = Modifier.padding(20.dp),
                                    backgroundColor = darkColorPalette().secondary
                            )

                        }

                        Column(
                                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(0.dp,20.dp),
                                horizontalGravity = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(text = "Don't have account?")

                            TextButton(
                                    onClick = { /*Aqui RegisterActivity*/},
                                    text = { Text(text ="Register Account") },
                                    contentColor = MaterialTheme.colors.secondary
                            )

                        }

                    }

            )
        }
    }

    private fun showMessage(message: String) {

        val duration = Toast.LENGTH_SHORT
        val toast =
                Toast.makeText(applicationContext, message, duration)
        toast.show()

    }

    private fun validateFields(email : TextFieldValue, password: TextFieldValue): Boolean{

        if(email.text == "" || password.text == ""){
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, "Must specify all the fields", duration)
            toast.show()
            return false
        }
        return true
    }


}