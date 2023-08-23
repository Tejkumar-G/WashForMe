package com.example.washforme.featureCart.domain.interfaces

interface ItemToCategory {
    fun itemsChanged(categoryId: String, allItemsRemoved: Boolean)
}