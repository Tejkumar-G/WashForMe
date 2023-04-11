package com.example.washforme.interfaces

interface ItemToCategory {
    fun itemsChanged(categoryId: Int, allItemsRemoved: Boolean)
}