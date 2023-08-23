package com.example.washforme.core.domain.useCase

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.domain.model.UserDetailsModel
import com.example.washforme.core.domain.repository.DashboardRepo
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val dashboardRepo: DashboardRepo
) {

    suspend operator fun invoke(): ResponseData<UserDetailsModel> =
        dashboardRepo.getUserDetails()
}