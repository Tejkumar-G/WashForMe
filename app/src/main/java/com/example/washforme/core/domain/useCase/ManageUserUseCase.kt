package com.example.washforme.core.domain.useCase

import com.example.washforme.core.data.dataSource.local.preferences.PrefConstants
import com.example.washforme.core.domain.enums.UserEnum
import com.example.washforme.core.domain.model.UserDetailsModel
import com.example.washforme.core.domain.preferences.PreferenceManager
import com.google.gson.Gson
import javax.inject.Inject

class ManageUserUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager,
) {

    operator fun invoke(options: UserEnum, user: UserDetailsModel? = null): UserDetailsModel? =
        when (options) {
            UserEnum.ADD -> addUser(user)
            UserEnum.REMOVE -> removeUser()
            UserEnum.GET -> getUser()
        }

    private fun getUser(): UserDetailsModel? {
        val userJson = preferenceManager.getString(PrefConstants.USER)
        if(userJson.isNullOrEmpty()) {
            return null
        }
        return Gson().fromJson(userJson, UserDetailsModel::class.java)
    }

    private fun removeUser(): UserDetailsModel? {
        preferenceManager.removeItem(PrefConstants.USER)
        return null
    }

    private fun addUser(user: UserDetailsModel?): UserDetailsModel? {
        preferenceManager.set(PrefConstants.USER, Gson().toJson(user))
        return null
    }
}