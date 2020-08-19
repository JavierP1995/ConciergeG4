package com.example.android.model

import java.util.*

/**
 * Record representation in android
 */
data class RecordModel (
    val kinship: String,
    val entryDate: Date?,
    val departureDate: Date?,
    val comment: String,
    val resident: String,
    val visits: String,
    val department: Int
)