package com.example.washforme.core.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washforme.core.data.dataSource.local.room.Cart
import com.example.washforme.core.domain.enums.UserEnum
import com.example.washforme.core.domain.model.Addres
import com.example.washforme.core.domain.model.UserDetailsModel
import com.example.washforme.core.domain.model.WashCategoryResponseModel
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.core.domain.model.WashItemResponseModel
import com.example.washforme.core.domain.model.WashItemResponseModelItem
import com.example.washforme.core.domain.useCase.CoreUseCase
import com.example.washforme.utils.baseViewModel.ViewModelUtils
import com.example.washforme.utils.baseViewModel.ViewModelUtilsImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreViewModel @Inject constructor(
    private val coreUseCase: CoreUseCase
) : ViewModel(), ViewModelUtils by ViewModelUtilsImpl() {

    private val _category = MutableStateFlow(WashCategoryResponseModel())
    val category = _category.asStateFlow()

//    private val _currentCart = MutableStateFlow<List<WashCategoryRelation>>(emptyList())
//    val currentCart = _currentCart.asStateFlow()

    private val _currentCart = MutableStateFlow<List<Cart>>(emptyList())
    val currentCart = _currentCart.asStateFlow()

    private val _user = MutableStateFlow<UserDetailsModel?>(null)
    val user = _user.asStateFlow()

    val address: StateFlow<Addres?> = user.map {
        it?.address?.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _currentCategory = MutableStateFlow(WashCategoryResponseModelItem())
    val currentCategory = _currentCategory.asStateFlow()

    private val _washItems = MutableStateFlow(WashItemResponseModel())
    val washItems = _washItems.asStateFlow()

    val totalPrice = currentCart.map { tempCategory ->
        Log.d("totalPriceTag", "totalPrice: value changed")
        tempCategory.sumOf { (it.items?.count ?: 0) * (it.items?.price ?: "0.0").toDouble()}
//        DecimalFormat("#.##").format(amount)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.00)

    val noOfItems = currentCart.map { tempCategory ->
        Log.d("totalPriceTag", "noOfItems: value changed")
        tempCategory.sumOf { it.items?.count ?: 0 }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    fun getCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            showLoader(true)
            val response = coreUseCase.getCategoryUseCase()
            if (response.isSuccess()) {
                response.payload?.let {
                    _category.emit(it)
                    _currentCategory.value = it[0]
                    getWashingItems(it[0].id)
                }
            } else {
                showToast(response.message.toString())
            }
            showLoader(false)
        }
    }

    fun getWashingItems(categoryId: String) {
        viewModelScope.launch {
            showLoader(true)
            val response = coreUseCase.getWashItemsUseCase(categoryId)
            if (response.isSuccess()) {
                _washItems.emit(
                    response.payload ?: WashItemResponseModel()
                )
            } else {
                _washItems.emit(WashItemResponseModel())
                showToast(response.message.toString())
            }
            showLoader(false)
        }
    }

    fun checkUserAlreadyLogIn(isUserLogin: (Boolean) -> Unit) {
        viewModelScope.launch {
            isUserLogin(coreUseCase.checkUserLoginUseCase())
        }
    }

    fun changeInTheObject(
        item: WashItemResponseModelItem,
    ) {
        viewModelScope.launch {
            coreUseCase.cart(item, currentCategory.value)
        }
    }

    fun getCart(category: WashCategoryResponseModelItem? = null) {
        viewModelScope.launch {
            val cartData = coreUseCase.getCart(category)
            Log.d("totalPriceTag", "getCart: before emit")
            _currentCart.emit(cartData)
         //   _currentCart.value = cartData
            Log.d("totalPriceTag", "getCart: after emit")
        }
//        _currentCart.value.forEach {cart ->
//            _washItems.value.filter { it.id == cart.items?.id }
//        }
    }

    fun setSelectedCategory(categoryId: WashCategoryResponseModelItem) {
        _currentCategory.update { categoryId }
    }

    init {
        getCategory()
        _user.value = coreUseCase.userManager(UserEnum.GET)
    }
}