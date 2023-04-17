package com.example.washforme.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washforme.R
import com.example.washforme.adapter.AddressAdapter
import com.example.washforme.db.Repository
import com.example.washforme.db.Status
import com.example.washforme.model.Address
import com.example.washforme.model.AddressType
import com.example.washforme.model.UserAddress
import com.example.washforme.utils.MyPreferenceManager
import com.example.washforme.utils.SingleLiveEvent
import com.google.android.material.chip.Chip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repo: Repository,
    val pref: MyPreferenceManager
) : ViewModel() {

    private val toast = MutableLiveData<String?>()
    private val isLoading = MutableLiveData(false)

    val addressAdapter = AddressAdapter(listOf(), this)

    val addressChanged = MutableLiveData(false)

    var userAddress: UserAddress? = null

    val addressForEditingSingleEvent = SingleLiveEvent<UserAddress?>()

    val isAddressTypeVisible = MutableLiveData(false)

    val exitFromScreen = MutableLiveData(false)


    private val _allAddresses: MutableLiveData<List<UserAddress>?> = MutableLiveData()
    val allAddresses: LiveData<List<UserAddress>?> = _allAddresses

    private val _addressType: MutableLiveData<String> = MutableLiveData()
    val addressType: LiveData<String> = _addressType


    fun getAllAddresses() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getAllAddresses().onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
                        _allAddresses.postValue(it.payload)
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

    fun changeCurrentAddress(id: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.changeCurrentAddress(id).onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
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


    fun saveAddress(
        addressLineOne: String?,
        addressLineTwo: String?,
        city: String?,
        postcode: String?,
        country: String?,
        otherAddressType: String?
    ) {
        val address = Address(
            addressLineOne ?: "",
            addressLineTwo ?: "",
            city ?: "",
            postcode?.toInt() ?: 0,
            country ?: ""
        )

        val addressType = this.addressType.value?.let {
            if (it == "other") {
                if (otherAddressType?.isEmpty() == true) {
                    Log.v("ERROR", "Can't call")
                    return
                }
                AddressType(otherAddressType)
            } else {
                AddressType(it)
            }


        }
        if (userAddress!=null) {
            userAddress?.address = address
            userAddress?.addressType = addressType
            userAddress?.let { updateCurrentAddress(it) }
        } else {
            val newUserAddress = UserAddress(address = address, addressType = addressType, currentAddress = false)
            createNewAddress(newUserAddress)
        }

    }

    private fun createNewAddress(userAddress: UserAddress) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.createNewAddress(userAddress).onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
                        exitFromScreen.postValue(true)
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

    private fun updateCurrentAddress(userAddress: UserAddress) {
        viewModelScope.launch(Dispatchers.Default) {
            userAddress.id?.let { id ->
                repo.updateCurrentAddress(id, userAddress).onEach {
                    when (it.status) {
                        Status.SUCCESS -> {
                            isLoading.postValue(false)
                            exitFromScreen.postValue(true)
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
    }


    fun changeAddressTypeView(currentChip: Chip, chipOne: Chip, chipTwo: Chip) {
        isAddressTypeVisible.value?.takeIf { it }?.let {
            isAddressTypeVisible.postValue(false)
        }
        currentChip.text.toString().lowercase().let {
            if (it != _addressType.value) {
                _addressType.postValue(it)
                return
            }

        }

        currentChip.isChecked = true
        chipOne.isChecked = false
        chipTwo.isChecked = false

        when (currentChip.id) {
            R.id.chipAdd -> isAddressTypeVisible.postValue(true)
        }
    }

    fun addAddressType(userAddress: UserAddress) {
        userAddress.addressType?.type?.let {
            when (it) {
                "home", "office" -> _addressType.postValue(it)
                else -> _addressType.postValue("other")
            }

        }
    }

    fun createAddress() {
        addressForEditingSingleEvent.postValue(UserAddress())
    }
}