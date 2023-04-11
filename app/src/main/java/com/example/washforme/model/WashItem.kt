package com.example.washforme.model

data class WashItem(
    var id: Int,
    var image: String,
    var name: String?,
    var price: Int,
    var count: Int,
)