package com.example.washforme.model

import com.google.gson.annotations.SerializedName

data class UserAddress(
    val id: Int?,
    val user: User?,
    var address: Address?,
    @SerializedName("address_type")
    var addressType: AddressType?,
    @SerializedName("current_address")
    var currentAddress: Boolean?
)
