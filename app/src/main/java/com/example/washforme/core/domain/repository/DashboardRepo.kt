package com.example.washforme.core.domain.repository

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.domain.model.UserDetailsModel
import com.example.washforme.core.domain.model.WashCategoryResponseModel
import com.example.washforme.core.domain.model.WashItemResponseModel

interface DashboardRepo {

    suspend fun getWashCategory(): ResponseData<WashCategoryResponseModel>

    suspend fun getWashItem(categoryId: String): ResponseData<WashItemResponseModel>

    suspend fun getUserDetails(): ResponseData<UserDetailsModel>
}