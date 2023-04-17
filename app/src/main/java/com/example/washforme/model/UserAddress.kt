package com.example.washforme.model

import com.google.gson.annotations.SerializedName

data class UserAddress(
    val id: Int?=null,
    val user: User?=null,
    var address: Address?=null,
    @SerializedName("address_type")
    var addressType: AddressType?=null,
    @SerializedName("current_address")
    var currentAddress: Boolean?=null
)
