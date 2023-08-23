package com.example.washforme.core.domain.useCase

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.domain.model.WashItemResponseModel
import com.example.washforme.core.domain.repository.DashboardRepo
import javax.inject.Inject

class GetWashingItemsUseCase  @Inject constructor(
    private val dashboardRepo: DashboardRepo
){
    suspend operator fun invoke(categoryId: String): ResponseData<WashItemResponseModel> {
        return dashboardRepo.getWashItem(categoryId)
    }
}