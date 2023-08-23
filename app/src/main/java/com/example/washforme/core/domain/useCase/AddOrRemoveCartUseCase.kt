package com.example.washforme.core.domain.useCase

import com.example.washforme.core.domain.enums.ItemsOption
import com.example.washforme.core.domain.model.WashCategoryRelation
import com.example.washforme.core.domain.model.WashItemResponseModelItem

class AddOrRemoveCartUseCase {

    operator fun invoke(
        cart: WashCategoryRelation,
        item: WashItemResponseModelItem,
        option: ItemsOption
    ): WashCategoryRelation {
        when (option) {
            ItemsOption.ADD -> {
                var isExists = false
                cart.items.forEach {
                    if (it.id == item.id) {
                        isExists = true
                        it.count = item.count
                    }
                }
                return if (isExists) {
                    cart
                } else {
                    cart.items.add(item)
                    cart
                }
            }

            ItemsOption.REMOVE -> {
                var removedPosition = -1
                cart.items.forEach {
                    if (it.id == item.id) {
                        it.count = item.count
                        if (it.count == 0)
                            removedPosition = cart.items.indexOf(it)
                    }
                }
                if (removedPosition != -1)
                    cart.items.removeAt(removedPosition)
                return cart
            }
        }
    }
}