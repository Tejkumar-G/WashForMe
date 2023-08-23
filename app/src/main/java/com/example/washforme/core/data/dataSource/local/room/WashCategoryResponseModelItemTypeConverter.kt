package com.example.washforme.core.data.dataSource.local.room

import androidx.room.TypeConverter
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.google.gson.Gson

class WashCategoryResponseModelItemTypeConverter {
    @TypeConverter
    fun fromWashCategoryResponseModelItem(item: WashCategoryResponseModelItem?): String? {
        return Gson().toJson(item)
    }

    @TypeConverter
    fun toWashCategoryResponseModelItem(json: String?): WashCategoryResponseModelItem? {
        return Gson().fromJson(json, WashCategoryResponseModelItem::class.java)
    }
}