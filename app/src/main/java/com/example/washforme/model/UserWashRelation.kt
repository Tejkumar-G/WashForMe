package com.example.washforme.model

data class UserWashRelation(
    var id: Int?,
    var user: User?,
    var wash_category_relations: List<WashCategoryRelation>?
)