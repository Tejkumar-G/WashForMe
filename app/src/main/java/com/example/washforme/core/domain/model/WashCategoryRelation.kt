package com.example.washforme.core.domain.model

data class WashCategoryRelation(
    var category: WashCategoryResponseModelItem?,
    var items: ArrayList<WashItemResponseModelItem>
) {
    constructor() : this(
        category = null,
        items = emptyList<WashItemResponseModelItem>() as ArrayList<WashItemResponseModelItem>
    )
}