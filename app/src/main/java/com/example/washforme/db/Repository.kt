package com.example.washforme.db

import androidx.lifecycle.MutableLiveData
import com.example.washforme.model.ModelHelper
import com.example.washforme.model.OtpValidation
import com.example.washforme.model.PhoneValidation
import com.example.washforme.utils.Constants
import com.example.washforme.utils.MyPreferenceManager
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    private val preferences: MyPreferenceManager
) {
    suspend fun validatePhone(phoneNo: String): MutableLiveData<ResponseData<Boolean>> {
        val result = MutableLiveData<ResponseData<Boolean>>()

        val response = api.validatePhone(PhoneValidation(phoneNo))

        result.value = ResponseData.loading(null)
        try {
            if (response.isSuccessful) {
                val status = response.body()?.status ?: false
                if (status) {
                    result.value = ResponseData.success(true)
                } else {
                    result.value = ResponseData.failure(response.errorBody().toString(), null)
                }
            } else {
                result.value = ResponseData.failure("Invalid Server Error", null)
            }
        } catch (e: Exception) {
            result.value = ResponseData.failure("Internal Error", null)
        }
        return result
    }

    suspend fun validateOTP(
        phoneNo: String,
        otp: String
    ): MutableLiveData<ResponseData<Boolean>> {
        val result = MutableLiveData<ResponseData<Boolean>>()
        val response = api.validateOTP(OtpValidation(phoneNo, otp))
        try {
            if (response.isSuccessful) {
                val token = response.body()?.let { it.token }
                token?.let { preferences.set(Constants.TOKEN, it) }
                result.value = ResponseData.success(null)
            } else {
                result.value = ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            result.value = ResponseData.failure("Internal Error", null)
        }
        return result
    }


}