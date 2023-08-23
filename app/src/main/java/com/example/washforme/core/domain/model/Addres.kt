package com.example.washforme.core.domain.model

data class Addres(
    var address_line_1: String?,
    var address_line_2: String?,
    var city: String?,
    var country: String?,
    var created_at: String?,
    var id: String?,
    var is_primary: Boolean?,
    var pincode: Int?,
    var type: String?,
    var updated_at: String?,
    var user: String?
)