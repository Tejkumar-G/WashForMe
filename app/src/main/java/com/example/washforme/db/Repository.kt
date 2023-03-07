package com.example.washforme.db

import androidx.lifecycle.MutableLiveData
import com.example.washforme.model.Categories
import com.example.washforme.model.OtpValidation
import com.example.washforme.model.PhoneValidation
import com.example.washforme.utils.Constants
import com.example.washforme.utils.MyPreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
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
        result.value = ResponseData.loading(null)
        try {
            if (response.isSuccessful) {
                val token = response.body()?.token
                val tokenExpiry = response.body()?.expiry
                token?.let { preferences.set(Constants.TOKEN, it) }
                tokenExpiry?.let { preferences.set(Constants.EXPIRY, it) }
                result.value = ResponseData.success(true)
            } else {
                result.value = ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            result.value = ResponseData.failure("Internal Error", null)
        }
        return result
    }

    suspend fun logoutUser(): MutableLiveData<ResponseData<Boolean>> {
        val result = MutableLiveData<ResponseData<Boolean>>()
        val response = api.logoutUser("Token ${preferences.getString(Constants.TOKEN).toString()}")
        result.value = ResponseData.loading(null)
        try {
            if (response.isSuccessful) {
                preferences.clear()
                result.value = ResponseData.success(true)
            } else {
                result.value = ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            result.value = ResponseData.failure("Internal Error", null)
        }
        return result
    }

    suspend fun getCategories(): Flow<ResponseData<Array<Categories>>> = flow {
        emit(ResponseData.loading(null))
        try {
            val response =
                api.getWashCategory("Token ${preferences.getString(Constants.TOKEN).toString()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResponseData.success(it))
                }
            }
        } catch (e: HttpException) {
            emit(
                ResponseData.failure(
                    msg = "Oops, something went wrong!",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                ResponseData.failure(
                    msg = "Couldn't reach server, check your internet connection.",
                    data = null
                )
            )
        } catch (e: NullPointerException) {
            emit(
                ResponseData.failure(
                    msg = "Oops, something went wrong!",
                    data = null
                )
            )
        }
    }
}