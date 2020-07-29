package com.example.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.material.Button
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import com.example.android.ui.AndroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

}





