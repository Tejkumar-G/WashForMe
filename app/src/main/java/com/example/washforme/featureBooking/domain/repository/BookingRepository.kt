package com.example.washforme.featureBooking.domain.repository

import com.example.washforme.core.data.ResponseData
import com.example.washforme.featureBooking.domain.models.PickUpSlotsModel

interface BookingRepository {

    suspend fun getPickUpSlots(): ResponseData<List<PickUpSlotsModel>>

    suspend fun getPickUpSlotsByDate(date: String): ResponseData<List<PickUpSlotsModel>>
}