package com.example.washforme.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.washforme.R
import com.example.washforme.db.Repository
import com.example.washforme.db.Status
import com.example.washforme.model.OtpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    private val _otpResponse = MutableLiveData<OtpResponse>()
    val otpResponse: LiveData<OtpResponse> = _otpResponse

    private val _validateOtpResponse = MutableLiveData<OtpResponse>()
    val validateOtpResponse: LiveData<OtpResponse> = _validateOtpResponse

    val phone = MutableLiveData<String>()
    val otp = MutableLiveData<String>()

    val toast = MutableLiveData<String?>()
    val isLoading = MutableLiveData(false)

    fun validatePhone(view: View) {
        view.findNavController().navigate(R.id.loginToOtpFragment)
//        if (phone.value != "") {
//            viewModelScope.launch(Dispatchers.Main) {
//                view.findViewTreeLifecycleOwner()?.let {
//                    isLoading.postValue(true)
//                    repo.validatePhone("+91" + phone.value)
//                        .observe(it) { response ->
//                            when (response.status) {
//                                Status.SUCCESS -> {
//                                    isLoading.postValue(false)
//                                    view.findNavController().navigate(R.id.loginToOtpFragment)
//                                }
//                                Status.FAILURE -> {
//                                    isLoading.postValue(false)
//
//                                    toast.value = response.message.toString()
//                                }
//                                Status.LOADING -> {
//                                    isLoading.postValue(false)
//
//                                }
//                                else -> Unit
//                            }
//                        }
//                }
//            }
//        }
    }

        fun verifyOTP(view: View) {
            Log.d("verifyOTP", "verifyOTP: work")
            if (otp.value != "") {
                viewModelScope.launch {
                    view.findViewTreeLifecycleOwner()?.let {
                        repo.validateOTP(phone.value.toString(), otp.value.toString())
                            .observe(it) { response ->
                                when (response.status) {
                                    Status.SUCCESS -> {
                                        _validateOtpResponse.value = OtpResponse(
                                            status = true,
                                            isLoading = false,
                                        )
                                    }
                                    Status.FAILURE -> {
                                        _validateOtpResponse.value = OtpResponse(
                                            status = false,
                                            isLoading = false,
                                        )
                                        toast.value = response.message.toString()
                                    }
                                    Status.LOADING -> {
                                        _validateOtpResponse.value = OtpResponse(
                                            status = false,
                                            isLoading = true,
                                        )
                                    }
                                    else -> Unit
                                }
                            }
                    }

                }
            }
        }
    }
