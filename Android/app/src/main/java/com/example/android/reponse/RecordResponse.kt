package com.example.android.reponse

import com.example.android.model.DepartmentModel
import com.example.android.model.RecordModel
import com.google.gson.annotations.SerializedName

data class RecordResponse (
    @SerializedName("message")
    var message:String,
    @SerializedName("records")
    var records: ArrayList<RecordModel>
)