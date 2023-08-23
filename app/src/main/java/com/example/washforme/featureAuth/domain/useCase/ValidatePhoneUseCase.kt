package com.example.washforme.featureAuth.domain.useCase

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.data.dataSource.local.preferences.PrefConstants
import com.example.washforme.core.domain.preferences.PreferenceManager
import com.example.washforme.featureAuth.domain.repository.AuthRepository
import com.example.washforme.utils.isPhoneNumber
import javax.inject.Inject

class ValidatePhoneUseCase @Inject constructor(
    private val authRepo: AuthRepository,
    private val preferenceManager: PreferenceManager
) {

    suspend operator fun invoke(phone: String): ResponseData<Boolean> =
        if (phone.isNotEmpty() && phone.isPhoneNumber()) {
            val response = authRepo.validatePhone(phone)
            if (response.isSuccess()) {
                preferenceManager.set(PrefConstants.PHONE_NO, phone)
                ResponseData.success(true)
            } else {
                ResponseData.failure(response.message.toString(), false)
            }
        } else {
            ResponseData.failure("Invalid phone number", false)
        }
}