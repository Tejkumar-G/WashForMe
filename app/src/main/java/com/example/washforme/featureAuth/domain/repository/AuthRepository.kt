package com.example.washforme.featureAuth.domain.repository

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.domain.model.UserDetailsModel
import com.example.washforme.featureAuth.domain.models.CreateOtpResponse
import com.example.washforme.featureAuth.domain.models.OtpResponseModel

interface AuthRepository {

    suspend fun validatePhone(phoneNo: String): ResponseData<CreateOtpResponse>

    suspend fun verifyOTP(phoneNo: String, otp: String): ResponseData<OtpResponseModel>

    suspend fun logoutUser(): ResponseData<Boolean>

    suspend fun getUser(): ResponseData<UserDetailsModel>
}