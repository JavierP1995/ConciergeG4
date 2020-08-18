package com.example.android.activities.save

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.android.R
import com.example.android.adapter.DepartmentAdapter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class   SaveDepartment : AppCompatActivity() {

    companion object{
        var token : String = ""

        fun setLoginData(authToken : String){
            this.token = authToken
            Log.v("TOKEN", this.token)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_department)

    }

    fun doRegister() {

        val inputNumber = findViewById<TextInputEditText>(R.id.txtNumber)
        val inputFloor = findViewById<TextInputEditText>(R.id.txtFloor)
        val inputBlock = findViewById<TextInputEditText>(R.id.txtBlock)

        val number: Int = inputNumber.text.toString().toInt()
        val floor: Int = inputFloor.text.toString().toInt()
        val block: CharArray = inputBlock.text.toString().toCharArray()


        lifecycleScope.launch {
            val department = withContext(Dispatchers.IO){
                DepartmentAdapter.registerDepartment(token, number = number, floor = floor, block = block[0])
            }
        }

    }

}