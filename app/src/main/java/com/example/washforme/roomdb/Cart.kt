package com.example.washforme.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cart")
data class Cart (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var category: String?,
    var items: String?
)