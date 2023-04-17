package com.example.washforme.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("last_name")
    var lastName: String?,
    var email: String?,
    var id: Int?,
    var phone: String?,
    @SerializedName("other_details")
    var settings: String?,
    @SerializedName("default_address")
    var defaultAddress: UserAddress?

)