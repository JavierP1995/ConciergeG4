package com.example.android.reponse

import com.example.android.model.DepartmentModel
import com.google.gson.annotations.SerializedName

data class DepartmentResponse(
    @SerializedName("message")
    var message:String,
    @SerializedName("departments")
    var departments: ArrayList<DepartmentModel>)