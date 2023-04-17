package com.example.washforme.utils

import android.content.SharedPreferences
import com.example.washforme.model.User
import com.google.gson.Gson
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

    fun getString(key: String): String {
        sharedPref.getString(key, null).let {
            if (it.isNullOrEmpty() && key == Constants.TOKEN) {
                return "5a91ea03cac43769602ae8ead7f07b09063577279507f0e8ce555a5b735182d6"
            }
            return it?:""
        }
    }

    fun getBoolean(key: String) = sharedPref.getBoolean(key, false)

    fun removeItem (key : String) = editor.remove(key).apply()

    fun clear() {
        editor.clear()
            .apply()
    }

    fun getUser(): User {
        val stringUser = getString(Constants.USER)
        return Gson().fromJson(stringUser, User::class.java)
    }

    fun setUser(user: User?) {
        set(Constants.USER, Gson().toJson(user))
    }


}