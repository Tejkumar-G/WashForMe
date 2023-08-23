package com.example.washforme.core.domain.model

data class WashItemResponseModelItem(
    val id: String,
    val name: String,
    var price: String,
    val image: String,
    var count: Int,
    val extras: String?,
    val created_at: String,
    val updated_at: String
)