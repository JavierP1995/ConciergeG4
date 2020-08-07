package com.example.android.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.android.activities.DepartmentActivity
import com.example.android.ui.utils.darkThemeColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                topBar()
                Menu()
            }
        }
    }

    @Preview
    @Composable
    private fun topBar() =
        MaterialTheme( colors = darkThemeColors) {
            TopAppBar(
                    title =
                    {
                        Text(text = "Concierge",
                                style = MaterialTheme.typography.h5)
                    },
                    backgroundColor = darkThemeColors.primary,
                    elevation = 0.dp
            )
        }

    @Composable
    private fun Menu() =
        Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalGravity = Alignment.CenterHorizontally
        ){
            Button(
                    text = { Text("Registros") },
                    backgroundColor = darkThemeColors.onPrimary,
                    modifier = Modifier.padding(0.dp,10.dp),
                    onClick = {
                        startActivity(
                                Intent(
                                        this@MainActivity,
                                        RecordMenu::class.java
                                ))
                    }
            )
            Button(
                    text = { Text("Residentes") },
                    backgroundColor = darkThemeColors.onPrimary,
                    modifier = Modifier.padding(0.dp,10.dp),
                    onClick = {
                        startActivity(
                                Intent(
                                        this@MainActivity,
                                        ResidentMenu::class.java
                                ))
                    }
            )
            Button(
                    text = { Text("Departamentos") },
                    backgroundColor = darkThemeColors.onPrimary,
                    modifier = Modifier.padding(0.dp,10.dp),
                    onClick = {
                        startActivity(
                                Intent(
                                        this@MainActivity,
                                        DepartmentMenu::class.java
                        ))
                    }
            )
            Button(
                    text = { Text("Visitas") },
                    backgroundColor = darkThemeColors.onPrimary,
                    modifier = Modifier.padding(0.dp,10.dp),
                    onClick = {
                        startActivity(
                                Intent(
                                        this@MainActivity,
                                        VisitMenu::class.java
                                ))
                    }
            )
        }

}






