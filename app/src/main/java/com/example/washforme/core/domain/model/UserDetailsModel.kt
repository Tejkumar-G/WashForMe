package com.example.washforme.core.domain.model

data class UserDetailsModel(
    var id: String?,
    var first_name: String?,
    var last_name: String?,
    var email: String?,
    var phone: String?,
    var is_phone_verified: Boolean?,
    var address: List<Addres?>?,
    var cart_total_price: String?,
    var other_details: String?
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}