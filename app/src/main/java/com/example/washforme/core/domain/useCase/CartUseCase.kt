package com.example.washforme.core.domain.useCase

import com.example.washforme.core.data.dataSource.local.room.Cart
import com.example.washforme.core.data.dataSource.local.room.CartDao
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.core.domain.model.WashItemResponseModelItem
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val cartDao: CartDao
) {

    suspend operator fun invoke(item: WashItemResponseModelItem?, category: WashCategoryResponseModelItem) {
        val cart = cartDao.getAll()
        val currentCategory = cart.filter { category.id == it.category?.id }
        if (currentCategory.isEmpty()) { // if cart is empty we are adding the added item to cart.
            cartDao.insert(
                Cart(category = category, items = item)
            )
        } else {
            if (item == null || item.count == 0) { // if item is null or the item count is 0 deleting the item from cart.
                currentCategory.forEach {
                    if(item?.id == it.items?.id) {
                        it.id?.let { id -> cartDao.delete(id) }
                    }
                }
            } else {
                var itemUpdated = false
                currentCategory.forEach {
                    if(item.id == it.items?.id) { // if item is not null and item is already present in category updating the item.
                        it.id?.let { it1 -> cartDao.update(item, it1) }
                        itemUpdated = true
                    }
                }
                if(!itemUpdated) { // if item is not updated then inserting it as new item.
                    cartDao.insert(
                        Cart(category = category, items = item)
                    )
                }
            }
        }
    }
}