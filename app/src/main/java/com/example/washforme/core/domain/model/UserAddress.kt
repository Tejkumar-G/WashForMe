package com.example.washforme.core.domain.model

import com.example.washforme.featureAuth.domain.models.User
import com.google.gson.annotations.SerializedName

data class UserAddress(
    val id: String?=null,
    val user: User?=null,
    var address: Address?=null,
    @SerializedName("address_type")
    var addressType: AddressType?=null,
    @SerializedName("current_address")
    var currentAddress: Boolean?=null
)
