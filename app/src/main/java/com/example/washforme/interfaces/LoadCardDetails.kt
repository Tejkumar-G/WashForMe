package com.example.washforme.interfaces

import com.example.washforme.model.WashCategoryRelation

interface LoadCardDetails {
    fun getCart(cart: ArrayList<WashCategoryRelation>)
}