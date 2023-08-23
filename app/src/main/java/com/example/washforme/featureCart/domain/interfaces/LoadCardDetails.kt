package com.example.washforme.featureCart.domain.interfaces

import com.example.washforme.core.domain.model.WashCategoryRelation

interface LoadCardDetails {
    fun getCart(cart: ArrayList<WashCategoryRelation>)
}