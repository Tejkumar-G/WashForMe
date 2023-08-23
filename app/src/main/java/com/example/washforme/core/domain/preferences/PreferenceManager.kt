package com.example.washforme.core.domain.preferences

interface PreferenceManager {

    fun set(key: String, value: String)

    fun set(key: String, value: Int)

    fun set(key: String, value: Boolean)

    fun set(key: String, value: Float)

    fun getString(key: String): String?

    fun getInt(key: String): Int

    fun getBoolean(key: String): Boolean

    fun removeItem (key : String)

    fun clear()
}