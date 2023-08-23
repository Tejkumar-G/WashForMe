package com.example.washforme.core.domain.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address_line_one")
    val addressLineOne: String?,
    @SerializedName("address_line_two")
    val addressLineTwo: String?,
    val city: String?,
    val postcode: Int?,
    val country: String?
)

