package com.example.washforme.featureAuth.domain.useCase

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.domain.enums.UserEnum
import com.example.washforme.core.domain.preferences.PreferenceManager
import com.example.washforme.core.domain.useCase.ManageUserUseCase
import com.example.washforme.featureAuth.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepo: AuthRepository,
    private val preferenceManager: PreferenceManager
) {

    suspend operator fun invoke(): ResponseData<Boolean> {
        val manageUser = ManageUserUseCase(preferenceManager)
        val response = authRepo.getUser()
        return if (response.isSuccess()) {
            response.payload?.let { manageUser(UserEnum.ADD, it) }
            ResponseData.success(true)
        } else {
            ResponseData.failure(response.message.toString(), false)
        }
    }
}