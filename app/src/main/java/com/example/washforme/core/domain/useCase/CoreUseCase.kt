package com.example.washforme.core.domain.useCase

import com.example.washforme.featureAuth.domain.useCase.CheckUserLoginUseCase

data class CoreUseCase(
    val checkUserLoginUseCase: CheckUserLoginUseCase,
    val getCategoryUseCase: GetCategoryUseCase,
    val getCategoryById: GetCategoryByIdUseCase,
    val getWashItemsUseCase: GetWashingItemsUseCase,
    val addOrRemoveItemUseCase: AddOrRemoveItemUseCase,
    val userManager: ManageUserUseCase,
    val cart: CartUseCase,
    val getCart: GetCartFromDBUseCase,
)
