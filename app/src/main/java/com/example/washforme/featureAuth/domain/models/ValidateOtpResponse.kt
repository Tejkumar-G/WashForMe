package com.example.washforme.featureAuth.domain.models

data class ValidateOtpResponse(
    var expiry: String?,
    var token: String?
)