package com.example.android.ui

import com.google.gson.annotations.SerializedName

data class DepartmentResponse (@SerializedName("number") var number:Int, @SerializedName("floor") var floor:Int, @SerializedName("block") var block:Char)