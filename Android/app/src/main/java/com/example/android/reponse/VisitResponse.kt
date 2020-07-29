package com.example.android.reponse

import com.example.android.model.RecordModel
import com.example.android.model.VisitModel
import com.google.gson.annotations.SerializedName

data class VisitResponse (
    @SerializedName("message")
    var message:String,
    @SerializedName("visits")
    var visits: ArrayList<VisitModel>
)