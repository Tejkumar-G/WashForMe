package com.example.washforme.featureAuth.presentation.login

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.washforme.R
import com.example.washforme.core.data.Status
import com.example.washforme.core.data.dataSource.local.preferences.PreferenceManagerImpl
import com.example.washforme.core.data.dataSource.remote.Repository
import com.example.washforme.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OldLoginViewModel @Inject constructor(
    private val repo: Repository,
    private val pref: PreferenceManagerImpl
) : ViewModel() {

    val phone = MutableLiveData<String>()
    val phoneNumber = MutableLiveData(Constants.COUNTRY_CODE)
    val otp = MutableLiveData<String>()
    val toast = MutableLiveData<String?>()
    val isLoading = MutableLiveData(false)

    fun writeToast(context: Context, message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            toast.value = null
        }
    }

    fun validatePhone(view: View) {
        if (phone.value != "") {
            viewModelScope.launch(Dispatchers.Main) {
                view.findViewTreeLifecycleOwner()?.let {
                    phoneNumber.value = phoneNumber.value + phone.value
                    pref.set("Phone", phoneNumber.value.toString())
                    repo.validatePhone(phoneNumber.value!!)
                        .observe(it) { response ->
                            when (response.status) {
                                Status.SUCCESS -> {
                                    isLoading.postValue(false)
                                    view.findNavController().navigate(R.id.loginToOtpFragment)
                                }
                                Status.FAILURE -> {
                                    isLoading.postValue(false)
                                    phoneNumber.value = ""
                                    pref.removeItem("Phone")
                                    toast.value = response.message.toString()
                                }
                                Status.LOADING -> {
                                    isLoading.postValue(true)
                                }
                            }
                        }
                }
            }
        }
    }

    fun verifyOTP(view: View) {
        Log.d("verifyOTP", "verifyOTP: work")
        phoneNumber.value = phoneNumber.value + phone.value
        if (otp.value != "") {
            viewModelScope.launch {
                view.findViewTreeLifecycleOwner()?.let {
                    repo.validateOTP(pref.getString("Phone").toString(), otp.value.toString())
                        .observe(it) { response ->
                            when (response.status) {
                                Status.SUCCESS -> {
                                    isLoading.postValue(false)
                                    view.findNavController()
                                        .navigate(R.id.otpFragmentToMainFragment)
                                }
                                Status.FAILURE -> {
                                    isLoading.postValue(false)
                                    toast.value = response.message.toString()
                                }
                                Status.LOADING -> {
                                    isLoading.postValue(true)
                                }
                            }
                        }
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getUser().onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
                        pref.setUser(it.payload)
                    }
                    Status.FAILURE -> {
                        isLoading.postValue(false)
                        toast.postValue(it.message.toString())
                    }
                    Status.LOADING -> {
                        isLoading.postValue(true)
                    }
                }
            }.launchIn(this)
        }
    }
//
//    fun getToken() =
//        pref.getString(Constants.TOKEN)
//
//    fun getUserName() =
//        pref.getString(Constants.USER)
}
