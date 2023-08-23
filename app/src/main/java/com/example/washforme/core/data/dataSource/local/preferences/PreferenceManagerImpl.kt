package com.example.washforme.core.data.dataSource.local.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.washforme.core.domain.preferences.PreferenceManager
import com.example.washforme.featureAuth.domain.models.User
import com.google.gson.Gson
import javax.inject.Inject

class PreferenceManagerImpl @Inject constructor(
    private val sharedPref: SharedPreferences
    ): PreferenceManager {

    override fun set(key: String, value: String) {
        sharedPref.edit { putString(key, value) }
    }

    override fun set(key: String, value: Int) {
        sharedPref.edit { putInt(key, value) }
    }

    override fun set(key: String, value: Boolean) {
        sharedPref.edit { putBoolean(key, value) }
    }

    override fun set(key: String, value: Float) {
        sharedPref.edit { putFloat(key, value) }
    }

    override fun getInt(key: String) = sharedPref.getInt(key, 0)

    override fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    override fun getBoolean(key: String) = sharedPref.getBoolean(key, false)

    override fun removeItem(key: String) =
        sharedPref.edit { remove(key) }

    override fun clear() {
        sharedPref.edit { clear() }
    }

    fun getUser(): User? {
        val stringUser = getString(PrefConstants.USER)
        if (stringUser == "")
            return null
        return Gson().fromJson(stringUser, User::class.java)
    }

    fun setUser(user: User?) {
        set(PrefConstants.USER, Gson().toJson(user))
    }
}