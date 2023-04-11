package com.example.washforme.model

import com.google.gson.annotations.SerializedName

data class Settings(
    val availability: String?,
    val notifications: String?,
    val language: String?,
    @SerializedName("dark_mode")
    val darkMode: String?
)