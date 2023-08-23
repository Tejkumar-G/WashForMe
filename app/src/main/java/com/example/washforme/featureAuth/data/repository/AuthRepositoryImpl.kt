package com.example.washforme.featureAuth.data.repository

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.domain.model.OtpValidation
import com.example.washforme.core.domain.model.PhoneValidation
import com.example.washforme.core.domain.model.UserDetailsModel
import com.example.washforme.core.domain.preferences.PreferenceManager
import com.example.washforme.featureAuth.data.dataSource.AuthApi
import com.example.washforme.featureAuth.domain.models.CreateOtpResponse
import com.example.washforme.featureAuth.domain.models.OtpResponseModel
import com.example.washforme.featureAuth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val preferences: PreferenceManager
) : AuthRepository {

    override suspend fun validatePhone(phoneNo: String): ResponseData<CreateOtpResponse> {
        val response = api.validatePhone(PhoneValidation(phoneNo))
        return try {
            if (response.isSuccessful) {
                ResponseData.success(response.body())
            } else {
                ResponseData.failure(response.message().toString(), null)
            }
        } catch (e: Exception) {
            ResponseData.failure("Internal Error", null)
        }
    }

    override suspend fun verifyOTP(
        phoneNo: String,
        otp: String
    ): ResponseData<OtpResponseModel> {
        val response = api.validateOTP(OtpValidation(phoneNo, otp))
        return try {
            if (response.isSuccessful) {
//                val token = response.body()?.token
//                val tokenExpiry = response.body()?.expiry
//                token?.let { preferences.set(Constants.TOKEN, it) }
//                tokenExpiry?.let { preferences.set(Constants.EXPIRY, it) }
                ResponseData.success(response.body())
            } else {
                ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            ResponseData.failure("Internal Error", null)
        }
    }

    override suspend fun logoutUser(): ResponseData<Boolean> {
        val response = api.logoutUser()
        return try {
            if (response.isSuccessful) {
                preferences.clear()
                ResponseData.success(true)
            } else {
                ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            ResponseData.failure("Internal Error", null)
        }
    }

    override suspend fun getUser(): ResponseData<UserDetailsModel> {
        val response = api.getUserDetails()
        return try {
            if (response.isSuccessful) {
                ResponseData.success(response.body())
            } else {
                ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            ResponseData.failure("Internal Error", null)
        }
    }
}