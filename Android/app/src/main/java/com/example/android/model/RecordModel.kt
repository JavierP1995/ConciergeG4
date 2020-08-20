package com.example.android.model

import java.util.*

/**
 * Record representation in android
 */
data class RecordModel (
    val kinship: String,
    val entryDate: String?,
    val departureDate: String?,
    val comment: String,
    val resident: String,
    val visit: String,
    val department: Int
)