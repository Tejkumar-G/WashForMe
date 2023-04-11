package com.example.washforme.db

import com.example.washforme.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

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
    ) : Response<User>

    @GET("/wash_categories/")
    suspend fun getWashCategory(
        @Header("Authorization") token: String
    ) : Response<List<Category>>

    @GET("/wash_items/")
    suspend fun getWashingItems(
        @Header("Authorization") token: String
    ) : Response<List<WashItem>>

    @GET("/wash_items/{pk}")
    suspend fun getWashingItems(
        @Header("Authorization") token: String,
        @Path("pk") id: Int
    ) : Response<List<WashItem>>

    @POST("/create_user_wash_relations/")
    suspend fun createUserWashRelation(
        @Header("Authorization") token: String,
        @Body
        categoryRelations: ArrayList<WashCategoryRelation>?
    ) : Response<UserWashRelation>

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

    @GET("/get_all_addresses/")
    suspend fun getAllAddresses(
        @Header("Authorization") token: String
    ): Response<List<UserAddress>>

    @PUT("/change_current_address/{id}/")
    suspend fun changeCurrentAddress(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<UserAddress>

    @PUT("/update_address/{id}/")
    suspend fun updateCurrentAddress(
        @Path("id") id: Int,
        @Body userAddress: UserAddress,
        @Header("Authorization") token: String
    ): Response<UserAddress>
}