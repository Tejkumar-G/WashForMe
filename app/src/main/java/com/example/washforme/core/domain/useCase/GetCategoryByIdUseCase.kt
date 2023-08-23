package com.example.washforme.core.domain.useCase

import com.example.washforme.core.domain.model.WashCategoryResponseModel
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem

class GetCategoryByIdUseCase {

    operator fun invoke(
        items: WashCategoryResponseModel,
        id: String
    ): WashCategoryResponseModelItem? =
        items.find { item -> item?.id == id }
}