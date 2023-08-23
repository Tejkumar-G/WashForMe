package com.example.washforme.core.domain.model

import com.example.washforme.featureAuth.domain.models.User

data class UserWashRelation(
    var id: Int?,
    var user: User?,
    var wash_category_relations: List<WashCategoryRelation>?
)