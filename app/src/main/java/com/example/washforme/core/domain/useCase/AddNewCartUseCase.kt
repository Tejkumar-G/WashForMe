package com.example.washforme.core.domain.useCase

import com.example.washforme.core.data.dataSource.local.room.Cart
import com.example.washforme.core.data.dataSource.local.room.CartDao
import com.example.washforme.core.domain.model.WashCategoryRelation
import javax.inject.Inject

class AddNewCartUseCase @Inject constructor(
    private val cartDao: CartDao
) {

    operator fun invoke(currentCart: List<WashCategoryRelation>) {
        val cart = emptyList<Cart>().toMutableList()
        currentCart.forEach {
//            cart += Cart(
//                category = it.category,
//                items = it.items
//            )
        }
        cartDao.insertNewCart(cart)
    }
}
