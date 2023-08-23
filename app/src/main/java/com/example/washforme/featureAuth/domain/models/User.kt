package com.example.washforme.featureAuth.domain.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: String?,

    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("last_name")
    val lastName: String?,

    val email: String?,

    val phone: String?,

    @SerializedName("cart_total_price")
    val cartTotalPrice: String?,

    @SerializedName("other_details")
    val otherDetails: String?,

    @SerializedName("is_phone_verified")
    val isPhoneVerified: Boolean?
)