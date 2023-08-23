package com.example.washforme.featureBooking.data.repository

import com.example.washforme.core.data.ResponseData
import com.example.washforme.featureBooking.data.dataSource.BookingApi
import com.example.washforme.featureBooking.domain.models.PickUpSlotsModel
import com.example.washforme.featureBooking.domain.repository.BookingRepository
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val bookingApi: BookingApi
) : BookingRepository {
    override suspend fun getPickUpSlots(): ResponseData<List<PickUpSlotsModel>> {
        val response = bookingApi.getPickUpSlots()
        return try {
            if (response.isSuccessful) {
                ResponseData.success(response.body())
            } else {
                ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            ResponseData.failure(e.message.toString(), null)
        }
    }

    override suspend fun getPickUpSlotsByDate(date: String): ResponseData<List<PickUpSlotsModel>> {
        val response = bookingApi.getPickUpSlotsByDate(date)
        return try {
            if (response.isSuccessful) {
                ResponseData.success(response.body())
            } else {
                ResponseData.failure(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            ResponseData.failure(e.message.toString(), null)
        }
    }
}