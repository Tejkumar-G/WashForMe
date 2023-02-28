package com.example.washforme.db

import com.example.washforme.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("validate_phone")
    suspend fun createOtp(
        @Body
        user: User
    ): Response<String>

    @POST("validate_otp")
    suspend fun checkOtp()

    @POST("register")
    suspend fun register()

    @POST("login")
    suspend fun login()
}