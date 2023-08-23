package com.example.washforme.featureAuth.domain.models

import com.google.gson.annotations.SerializedName

data class OtpResponseModel(
    @SerializedName("access")
    val token: String?,

    val refresh: String?,

    val user: User?
)