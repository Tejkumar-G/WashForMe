package com.example.washforme.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washforme.db.Repository
import com.example.washforme.db.Status
import com.example.washforme.model.PickUpSlots
import com.example.washforme.utils.MyPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotViewModel@Inject constructor(
    private val repo: Repository,
    private val pref: MyPreferenceManager
): ViewModel() {

    val isLoading = MutableLiveData(false)
    private val _pickUpSlots: MutableLiveData<PickUpSlots> = MutableLiveData()
    val pickUpSlots: LiveData<PickUpSlots> = _pickUpSlots

    fun getPickUpSlots() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getPickUpSlots().onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
                        it.payload?.let { slots ->
                            _pickUpSlots.postValue(slots)
                        }

                    }
                    Status.FAILURE -> {
                        isLoading.postValue(false)
                    }
                    Status.LOADING -> {
                        isLoading.postValue(true)
                    }
                }
            }.launchIn(this)
        }
    }
}