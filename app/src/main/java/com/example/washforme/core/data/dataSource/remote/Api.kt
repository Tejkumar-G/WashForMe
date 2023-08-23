package com.example.washforme.core.data.dataSource.remote

import com.example.washforme.core.domain.model.*
import com.example.washforme.featureAuth.domain.models.CreateOtpResponse
import com.example.washforme.featureAuth.domain.models.User
import com.example.washforme.featureAuth.domain.models.ValidateOtpResponse
import com.example.washforme.featureBooking.domain.models.PickUpSlots
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("validate_phone/")
    suspend fun validatePhone(
        @Body
        user: PhoneValidation
    ): Response<CreateOtpResponse>

    @POST("validate_otp/")
    suspend fun validateOTP(
        @Body
        user: OtpValidation
    ): Response<ValidateOtpResponse>

    @POST("logout/")
    suspend fun logoutUser(
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    @GET("user_details/")
    suspend fun getUserDetails(
        @Header("Authorization") token: String
    ) : Response<User>

    @GET("core/api/categories/")
    suspend fun getWashCategory() : Response<WashCategoryResponseModel>

    @GET("wash_items/")
    suspend fun getWashingItems(
        @Header("Authorization") token: String
    ) : Response<List<WashItem>>

    @GET("wash_items/{pk}")
    suspend fun getWashingItems(
        @Header("Authorization") token: String,
        @Path("pk") id: Int
    ) : Response<List<WashItem>>

    @POST("create_user_wash_relations/")
    suspend fun createUserWashRelation(
        @Header("Authorization") token: String,
        @Body
        categoryRelations: ArrayList<WashCategoryRelation>?
    ) : Response<UserWashRelation>

    @GET("wash_category_item_relations/")
    suspend fun getWashCategoryItemRelations(
        @Header("Authorization") token: String
    ) : Response<WashCategoryItemRelationsResponse>

    @GET("user_wash_relations/")
    suspend fun getUserWashRelation(
        @Header("Authorization") token: String
    ) : Response<UserWashRelation>



    @POST("book_slot/")
    suspend fun slotBooking(
        @Header("Authorization") token: String,
        @Body
        userWashRelationId : Int,
        slot : Int
    ) : Response<SlotBookingResponse>

    @GET("core/api/address/")
    suspend fun getAllAddresses(
        @Header("Authorization") token: String
    ): Response<List<UserAddress>>

    @PUT("core/api/address/{id}/")
    suspend fun changeCurrentAddress(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<UserAddress>

    @PATCH("core/api/address/{id}/")
    suspend fun updateCurrentAddress(
        @Path("id") id: String,
        @Body userAddress: UserAddress,
        @Header("Authorization") token: String
    ): Response<UserAddress>

    @POST("core/api/address/")
    suspend fun createNewAddress(
        @Body userAddress: UserAddress,
        @Header("Authorization") token: String
    ): Response<UserAddress>

    @GET("get_pick_up_time_slots/")
    suspend fun getPickUpSlots(
        @Header("Authorization") token: String
    ): Response<List<PickUpSlots>>

    @GET("get_pick_up_slots/{date}/")
    suspend fun getPickUpSlotsByDate(
        @Header("Authorization") token: String,
        @Path("date") date: String
    ): Response<List<PickUpSlots>>
}