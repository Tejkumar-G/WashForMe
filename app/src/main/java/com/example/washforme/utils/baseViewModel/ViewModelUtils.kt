package com.example.washforme.utils.baseViewModel

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ViewModelUtils {
    val isLoading: StateFlow<Boolean>

    val toast: SharedFlow<String>

    suspend fun showLoader(loading: Boolean)
    suspend fun showToast(message: String)
}