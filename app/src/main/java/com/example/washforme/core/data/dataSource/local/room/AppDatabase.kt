package com.example.washforme.core.data.dataSource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Cart::class], version = 1)
@TypeConverters(WashItemResponseModelItemTypeConverter::class, WashCategoryResponseModelItemTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

}