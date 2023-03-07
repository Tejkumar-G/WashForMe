package com.example.washforme.db

import com.example.washforme.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    @POST("/validate_phone/")
    suspend fun validatePhone(
        @Body
        user: PhoneValidation
    ): Response<CreateOtpResponse>

    @POST("/validate_otp/")
    suspend fun validateOTP(
        @Body
        user: OtpValidation
    ): Response<ValidateOtpResponse>

    @POST("/logout/")
    suspend fun logoutUser(
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    @GET("/user_details/")
    suspend fun getUserDetails(
        @Header("Authorization") token: String
    ) : ModelHelper

    @GET("/wash_categories/")
    suspend fun getWashCategory(
        @Header("Authorization") token: String
    ) : Response<Array<Categories>>

    @GET("/wash_items/")
    suspend fun getWashingItems(
        @Header("Authorization") token: String
    ) : Response<List<WashingItems>>

    @POST("/create_user_wash_relations/")
    suspend fun createUserWashRelation(
        @Body
        Item : WashCategoryItemRelations
    ) : Response<CreateWashCategoryItemRelationsResponse>

    @GET("/wash_category_item_relations/")
    suspend fun getWashCategoryItemRelations(
        @Header("Authorization") token: String
    ) : Response<WashCategoryItemRelationsResponse>

    @GET("/user_wash_relations/")
    suspend fun getUserWashRelation(
        @Header("Authorization") token: String
    ) : Response<UserWashRelation>

    @GET("/get_time_slots/")
    suspend fun getTimeSlots(
        @Header("Authorization") token: String
    ) : Response<GetTimeSlotResponse>

    @POST("/book_slot/")
    suspend fun slotBooking(
        @Header("Authorization") token: String,
        @Body
        userWashRelationId : Int,
        slot : Int
    ) : Response<SlotBookingResponse>
}