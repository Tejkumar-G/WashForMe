package com.example.washforme.core.data.repository

import com.example.washforme.core.data.ResponseData
import com.example.washforme.core.data.dataSource.remote.DashboardApi
import com.example.washforme.core.domain.model.UserDetailsModel
import com.example.washforme.core.domain.model.WashCategoryResponseModel
import com.example.washforme.core.domain.model.WashItemResponseModel
import com.example.washforme.core.domain.preferences.PreferenceManager
import com.example.washforme.core.domain.repository.DashboardRepo
import javax.inject.Inject

class DashboardRepoImpl @Inject constructor(
    private val api: DashboardApi,
    private val preferenceManager: PreferenceManager
) : DashboardRepo {

    override suspend fun getWashCategory(): ResponseData<WashCategoryResponseModel> = try {
        val response = api.getWashCategory()

        if (response.isSuccessful) {
            response.body()?.let {
                ResponseData.success(it)
            } ?: ResponseData.failure("Response data is empty", null)
        } else {
            ResponseData.failure(response.message().toString(), null);
        }
    } catch (e: Exception) {
        ResponseData.failure(e.message.toString(), null)
    }

    override suspend fun getWashItem(categoryId: String): ResponseData<WashItemResponseModel> = try {
        val response = api.getWashItems(categoryId)
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseData.success(it)
            } ?: ResponseData.failure("Response data is empty", null)
        } else {
            ResponseData.failure(response.message().toString(), null);
        }
    } catch (e: Exception) {
        ResponseData.failure(e.message.toString(), null)
    }

    override suspend fun getUserDetails(): ResponseData<UserDetailsModel> = try {
        val response = api.getUserDetails()

        if (response.isSuccessful) {
            response.body()?.let {
                ResponseData.success(it)
            } ?: ResponseData.failure("Response data is empty", null)
        } else {
            ResponseData.failure(response.message().toString(), null);
        }
    } catch (e: Exception) {
        ResponseData.failure(e.message.toString(), null)
    }
}