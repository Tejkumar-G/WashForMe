package com.example.washforme.di

import com.example.washforme.core.data.dataSource.local.room.CartDao
import com.example.washforme.core.domain.preferences.PreferenceManager
import com.example.washforme.core.domain.repository.DashboardRepo
import com.example.washforme.core.domain.useCase.AddNewCartUseCase
import com.example.washforme.core.domain.useCase.AddOrRemoveCartUseCase
import com.example.washforme.core.domain.useCase.AddOrRemoveItemUseCase
import com.example.washforme.core.domain.useCase.CartUseCase
import com.example.washforme.core.domain.useCase.CoreUseCase
import com.example.washforme.core.domain.useCase.GetCartFromDBUseCase
import com.example.washforme.core.domain.useCase.GetCartUseCase
import com.example.washforme.core.domain.useCase.GetCategoryByIdUseCase
import com.example.washforme.core.domain.useCase.GetCategoryUseCase
import com.example.washforme.core.domain.useCase.GetWashingItemsUseCase
import com.example.washforme.core.domain.useCase.ManageUserUseCase
import com.example.washforme.featureAuth.domain.repository.AuthRepository
import com.example.washforme.featureAuth.domain.useCase.CheckUserLoginUseCase
import com.example.washforme.featureAuth.domain.useCase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideCoreUseCase(
        cartDao: CartDao,
        preferenceManager: PreferenceManager,
        authRepo: AuthRepository,
        dashboardRepo: DashboardRepo
    ) = CoreUseCase(
        checkUserLoginUseCase = CheckUserLoginUseCase(
            preferenceManager, GetUserUseCase(
                authRepo, preferenceManager
            )
        ),
        getCategoryUseCase = GetCategoryUseCase(dashboardRepo),
        getCategoryById = GetCategoryByIdUseCase(),
        getWashItemsUseCase = GetWashingItemsUseCase(dashboardRepo),
        addOrRemoveItemUseCase = AddOrRemoveItemUseCase(
            AddNewCartUseCase(cartDao), GetCartUseCase(), AddOrRemoveCartUseCase()
        ),
        userManager = ManageUserUseCase(preferenceManager),
        cart = CartUseCase(cartDao),
        getCart = GetCartFromDBUseCase(cartDao)
    )
}