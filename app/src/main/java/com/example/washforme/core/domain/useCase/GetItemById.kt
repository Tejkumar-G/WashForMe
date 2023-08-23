package com.example.washforme.core.domain.useCase

import com.example.washforme.core.domain.model.WashItem

class GetItemById {

    operator fun invoke(items: List<WashItem>, id: Int): WashItem? {
        items.onEach { item ->
            if (item.id == id) return item
        }
        return null
    }
}