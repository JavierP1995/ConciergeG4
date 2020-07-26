package com.example.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.Spacer
import androidx.ui.material.Button
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.ui.AndroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

@Composable
fun Greeting(name: String) {
    var click = 0
    Column() {
        TopAppBar(title = {
            Text(text = "Hello $name!")})

        Button(onClick = {click++}) {
            Text(text = "Click me !")
        }
        Text(text = "Click number $click !")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidTheme {
        Greeting("Android")
    }
}

private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}