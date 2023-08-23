package com.example.washforme.featureBooking.data.dataSource

import com.example.washforme.featureBooking.domain.models.PickUpSlotsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingApi {

    @GET("core/api/pickup-timeslots/")

    suspend fun getPickUpSlots(@Query("shop_id") id: String = "1"): Response<List<PickUpSlotsModel>>

    @GET("core/api/pickup-timeslots/{date}/")
    suspend fun getPickUpSlotsByDate(@Path("date") date: String): Response<List<PickUpSlotsModel>>
}