package com.example.washforme.core.domain.useCase

import android.util.Log
import com.example.washforme.core.domain.enums.ItemsOption
import com.example.washforme.core.domain.model.WashCategoryRelation
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.core.domain.model.WashItemResponseModelItem
import javax.inject.Inject

class AddOrRemoveItemUseCase @Inject constructor(
    private val addCartToDb: AddNewCartUseCase,
    private val getCart: GetCartUseCase,
    private val addToCart: AddOrRemoveCartUseCase,
) {

    operator fun invoke(
        options: ItemsOption,
        washItem: WashItemResponseModelItem,
        category: WashCategoryResponseModelItem?,
        currentCart: List<WashCategoryRelation>
    ): List<WashCategoryRelation> {
        Log.d("addOrRemoveTag", "AddOrRemoveItemUseCase category: ${category.toString()} currentCart: $currentCart}")
        val cartPosition = category?.let { getCart(currentCart, it.id) } ?: -1
        val tempCart: MutableList<WashCategoryRelation> = currentCart.toMutableList()

        when (options) {
            ItemsOption.ADD -> {
                Log.d("addOrRemoveTag", "AddOrRemoveItemUseCase ADD: $washItem cartPosition: $cartPosition}")
                when (cartPosition) {
                    -1 ->
                        tempCart += WashCategoryRelation(category, arrayListOf(washItem))

                    else -> {
                        Log.d("addOrRemoveTag1", "AddOrRemoveItemUseCase " +
                                "currentCart: $currentCart " +
                                "washItem: $washItem}")
                        tempCart[cartPosition] =
                            addToCart(currentCart[cartPosition], washItem, options)
                        Log.d("addOrRemoveTag1", "AddOrRemoveItemUseCase " +
                                "currentCart: $currentCart " +
                                "washItem: $washItem}")
                    }
                }
            }

            ItemsOption.REMOVE -> {
                Log.d("addOrRemoveTag", "AddOrRemoveItemUseCase ADD: $washItem cartPosition: $cartPosition}")
                when (cartPosition) {
                    -1 ->
                        tempCart -= WashCategoryRelation(category, arrayListOf(washItem))

                    else -> tempCart[cartPosition] =
                        addToCart(currentCart[cartPosition], washItem, options)
                }
            }
        }
        addCartToDb(tempCart)
        val mlist = mutableListOf<WashCategoryRelation>()
        mlist.addAll(tempCart)
        return mlist
    }
}