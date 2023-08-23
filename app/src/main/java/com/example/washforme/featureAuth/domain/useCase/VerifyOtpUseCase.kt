package com.example.washforme.featureAuth.domain.useCase

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.data.dataSource.local.preferences.PrefConstants
import com.example.washforme.core.domain.preferences.PreferenceManager
import com.example.washforme.featureAuth.domain.models.OtpResponseModel
import com.example.washforme.featureAuth.domain.repository.AuthRepository
import com.example.washforme.utils.isPhoneNumber
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val authRepo: AuthRepository,
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(phoneNumber: String, otp: String): ResponseData<OtpResponseModel> {
        val response = authRepo.verifyOTP(phoneNumber, otp)
        return if(!phoneNumber.isPhoneNumber() && otp.isEmpty()) {
            ResponseData.failure("Invalid PhoneNumber or OTP", null)
        } else {
            if(response.isSuccess()) {
                response.payload?.apply {
                    token?.let { preferenceManager.set(PrefConstants.TOKEN, it) }
                    user?.id?.let { preferenceManager.set(PrefConstants.USER_ID, it) }
                } ?: ResponseData.failure("Response is Empty", null)
            }
            response
        }
    }
}