package com.example.washforme.core.data.dataSource.local.room

import androidx.room.TypeConverter
import com.example.washforme.core.domain.model.WashItemResponseModelItem
import com.google.gson.Gson

class WashItemResponseModelItemTypeConverter {
    @TypeConverter
    fun fromWashItemResponseModelItem(item: WashItemResponseModelItem?): String? {
        return Gson().toJson(item)
    }

    @TypeConverter
    fun toWashItemResponseModelItem(json: String?): WashItemResponseModelItem? {
        return Gson().fromJson(json, WashItemResponseModelItem::class.java)
    }
}