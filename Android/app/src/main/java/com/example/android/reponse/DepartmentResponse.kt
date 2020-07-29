package com.example.android.reponse

import com.google.gson.annotations.SerializedName

data class DepartmentResponse(@SerializedName("status") var number:Int, @SerializedName("message") var floor:Int)