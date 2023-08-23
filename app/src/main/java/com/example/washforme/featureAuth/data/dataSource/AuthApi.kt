package com.example.washforme.featureAuth.data.dataSource

import com.example.washforme.core.domain.model.*
import com.example.washforme.featureAuth.domain.models.CreateOtpResponse
import com.example.washforme.featureAuth.domain.models.OtpResponseModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @GET("core/api/user_details/")
    suspend fun getUserDetails(): Response<UserDetailsModel>

    @POST("core/api/send_otp/")
    suspend fun validatePhone(@Body user: PhoneValidation): Response<CreateOtpResponse>

    @POST("core/api/otp_login/")
    suspend fun validateOTP(@Body user: OtpValidation): Response<OtpResponseModel>

    @POST("logout/")
    suspend fun logoutUser(): Response<ResponseBody>
}