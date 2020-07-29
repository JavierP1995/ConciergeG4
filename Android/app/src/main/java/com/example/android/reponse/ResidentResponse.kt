package com.example.android.reponse

import com.example.android.model.RecordModel
import com.example.android.model.ResidentModel
import com.google.gson.annotations.SerializedName

data class ResidentResponse (
    @SerializedName("message")
    var message:String,
    @SerializedName("residents")
    var residents: ArrayList<ResidentModel>

)