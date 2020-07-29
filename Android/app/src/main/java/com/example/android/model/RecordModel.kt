package com.example.android.model

import java.util.*

data class RecordModel (
    val kindship: String,
    val entryDate: Date,
    val departureDate: Date,
    val comment: String,
    val residend_id: Int,
    val visit_id: Int,
    val department_id: Int
)