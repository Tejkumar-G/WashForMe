package com.example.washforme.featureAuth.domain.useCase

import com.example.washforme.core.data.dataSource.local.preferences.PrefConstants
import com.example.washforme.core.domain.preferences.PreferenceManager
import javax.inject.Inject

class CheckUserLoginUseCase @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): Boolean {
        val isTokenAvailable = preferenceManager.getString(PrefConstants.TOKEN)?.isNotEmpty() ?: false
        val isUserDetailsNotAvailable =
            preferenceManager.getString(PrefConstants.USER).isNullOrEmpty()
        if (isTokenAvailable && isUserDetailsNotAvailable) {
            getUserUseCase()
        }
        return isTokenAvailable
    }
}

