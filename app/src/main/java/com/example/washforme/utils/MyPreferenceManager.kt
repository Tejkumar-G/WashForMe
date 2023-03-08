package com.example.washforme.utils

import android.content.SharedPreferences
import javax.inject.Inject

class MyPreferenceManager @Inject constructor(private val sharedPref : SharedPreferences) {

    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun set(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun set(key: String, value: Int) {
        editor.putInt(key, value)
            .apply()
    }

    fun set(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun set(key: String, value: Float) {
        editor.putFloat(key, value)
            .apply()
    }

    fun getInt(key: String) = sharedPref.getInt(key, 0)

    fun getString(key: String) = sharedPref.getString(key, "5a91ea03cac43769602ae8ead7f07b09063577279507f0e8ce555a5b735182d6")

    fun getBoolean(key: String) = sharedPref.getBoolean(key, false)

    fun removeItem (key : String) = editor.remove(key).apply()

    fun clear() {
        editor.clear()
            .apply()
    }
}