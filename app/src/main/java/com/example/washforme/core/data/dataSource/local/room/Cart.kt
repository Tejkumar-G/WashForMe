package com.example.washforme.core.data.dataSource.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.core.domain.model.WashItemResponseModelItem

@Entity(tableName = "cart")
@TypeConverters(WashItemResponseModelItemTypeConverter::class, WashCategoryResponseModelItemTypeConverter::class)
data class Cart (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var category: WashCategoryResponseModelItem?,
    var items: WashItemResponseModelItem?
)