package com.example.android.model

import com.google.gson.annotations.SerializedName


data class DepartmentModel(@SerializedName("number") var number:Int, @SerializedName("floor") var floor:Int, @SerializedName("block") var block:Char)
