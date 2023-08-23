package com.example.washforme.core.domain.useCase

import com.example.washforme.core.data.dataSource.local.room.Cart
import com.example.washforme.core.data.dataSource.local.room.CartDao
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import javax.inject.Inject

class GetCartFromDBUseCase @Inject constructor(
    private val cartDao: CartDao
) {
    operator fun invoke(category: WashCategoryResponseModelItem?): List<Cart> {
        return if(category == null) {
            cartDao.getAll()
        } else {
            cartDao.getCart(category)
        }
    }
}