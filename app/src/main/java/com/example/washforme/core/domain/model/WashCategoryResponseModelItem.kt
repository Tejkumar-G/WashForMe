package com.example.washforme.core.domain.model

data class WashCategoryResponseModelItem(
    val id: String,
    val name: String,
    val extra_per_item: String,
    val created_at: String,
    val updated_at: String
) {
    constructor() : this(
        created_at = "",
        extra_per_item = "",
        id = "",
        name = "",
        updated_at = ""
    )
}