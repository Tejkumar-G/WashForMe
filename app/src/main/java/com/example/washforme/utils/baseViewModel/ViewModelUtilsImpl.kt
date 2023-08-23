package com.example.washforme.utils.baseViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class ViewModelUtilsImpl: ViewModelUtils {
    private val _isLoading = MutableStateFlow(false)
    override val isLoading = _isLoading.asStateFlow()

    private val _toast = MutableSharedFlow<String>()
    override val toast = _toast.asSharedFlow()

    override suspend fun showLoader(loading: Boolean) {
        withContext(Dispatchers.Main) {
            _isLoading.emit(loading)
        }
    }

    override suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            _toast.emit(message)
        }
    }
}