package com.example.washforme.core.data.dataSource.remote

import com.example.washforme.core.domain.model.UserDetailsModel
import com.example.washforme.core.domain.model.WashCategoryResponseModel
import com.example.washforme.core.domain.model.WashItemResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardApi {

    @GET("core/api/categories/")
    suspend fun getWashCategory() : Response<WashCategoryResponseModel>

    @GET("core/api/items/")
    suspend fun getWashItem() : Response<WashItemResponseModel>

    @GET("core/api/items/category/{category_id}/")
    suspend fun getWashItems(@Path("category_id") id: String) : Response<WashItemResponseModel>

    @GET("core/api/user_details/")
    suspend fun getUserDetails() : Response<UserDetailsModel>

}