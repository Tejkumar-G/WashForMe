package com.example.washforme.core.domain.model

data class PhoneValidation(
    val phone: String? = null
)

data class OtpValidation (
     val phone : String? = null,
     val otp : String? = null
)
