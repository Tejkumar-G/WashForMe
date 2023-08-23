package com.example.washforme.core.domain.useCase

import com.example.washforme.core.domain.model.WashCategoryRelation

class GetCartUseCase {
    operator fun invoke(cartItems: List<WashCategoryRelation>, categoryId: String): Int =
        cartItems.indexOf(
            cartItems.find { it.category?.id == categoryId }
        )
}