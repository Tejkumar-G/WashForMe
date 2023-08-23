package com.example.washforme.featureBooking.domain.useCase

import com.example.washforme.core.data.ResponseData
import com.example.washforme.featureBooking.domain.models.PickUpSlotsModel
import com.example.washforme.featureBooking.domain.repository.BookingRepository
import javax.inject.Inject

class GetSlotsUseCase @Inject constructor(
    private val repo: BookingRepository
) {

    suspend operator fun invoke(): ResponseData<List<PickUpSlotsModel>> {
        val response = repo.getPickUpSlots()
        return try {
            if(response.isSuccess()) {
                response.payload?.let {
                    response
                } ?: ResponseData.failure("Response is Empty", null)
            } else {
                ResponseData.failure(response.message.toString(), null)
            }
        } catch(e: Exception) {
            ResponseData.failure("Internal Error", null)
        }
    }
}