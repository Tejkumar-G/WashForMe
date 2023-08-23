package com.example.washforme.featureAuth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washforme.featureAuth.domain.useCase.GetUserUseCase
import com.example.washforme.featureAuth.domain.useCase.ValidatePhoneUseCase
import com.example.washforme.featureAuth.domain.useCase.VerifyOtpUseCase
import com.example.washforme.featureAuth.utils.AuthConstants
import com.example.washforme.utils.baseViewModel.ViewModelUtils
import com.example.washforme.utils.baseViewModel.ViewModelUtilsImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
) : ViewModel(), ViewModelUtils by ViewModelUtilsImpl() {

    val phoneNumber = MutableStateFlow("")

    val _otp = MutableStateFlow(List(4) { "" })

    val countryCode = MutableStateFlow("")

    private val _navigation = MutableSharedFlow<String>()
    val navigation = _navigation.asSharedFlow()

    fun validatePhone() {
        viewModelScope.launch(Dispatchers.IO) {
            showLoader(true)
            val response = validatePhoneUseCase("${countryCode.value}${phoneNumber.value}")
            if (response.isSuccess()) {
                _navigation.emit(AuthConstants.NAV_TO_OTP_FRAGMENT)
            } else {
                showToast(response.message.toString())
            }
            showLoader(false)
        }
    }

    fun getUser() {
        viewModelScope.launch {
           showLoader(true)
            val response = getUserUseCase()
            if(!response.isSuccess()) {
                showToast(response.message.toString())
            }
            showLoader(false)
        }
    }

    fun verifyOTP() {
        viewModelScope.launch {
            showLoader(true)
            val response = verifyOtpUseCase("${countryCode.value}${phoneNumber.value}", _otp.value.joinToString( separator = ""))
            if(response.isSuccess()) {
                _navigation.emit(AuthConstants.NAV_TO_MAIN_FRAGMENT)
            } else {
                showToast(response.message.toString())
            }
            showLoader(false)
        }
    }

    fun updateOtpDigit(index: Int, digit: String) {
        if (index in 0 until 4) {
            val currentOtp = _otp.value.toMutableList()
            currentOtp[index] = digit
            _otp.value = currentOtp
        }
    }
}