package com.example.washforme.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washforme.adapter.SlotDateAdapter
import com.example.washforme.adapter.SlotTimeAdapter
import com.example.washforme.db.Repository
import com.example.washforme.db.Status
import com.example.washforme.model.PickUpSlots
import com.example.washforme.utils.MyPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SlotViewModel@Inject constructor(
    private val repo: Repository,
    private val pref: MyPreferenceManager
): ViewModel() {

    val isLoading = MutableLiveData(false)
    private val _pickUpSlots: MutableLiveData<List<PickUpSlots>> = MutableLiveData()
    val pickUpSlots: LiveData<List<PickUpSlots>> = _pickUpSlots

    private val _pickUpSlotsForTime: MutableLiveData<List<PickUpSlots>> = MutableLiveData()
    val pickUpSlotsForTime: LiveData<List<PickUpSlots>> = _pickUpSlotsForTime

    val isSameAddress : MutableLiveData<Boolean> = MutableLiveData(false)

    val slotDateAdapter = SlotDateAdapter(listOf(), this)

    val slotTimeAdapter = SlotTimeAdapter(listOf(), this)

    fun sameAddressCheckStatusChanged(isChecked: Boolean) {
        isSameAddress.value = isChecked
        Log.d("isChecked", "sameAddressCheckStatusChanged: $isChecked")
    }
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

    fun getPickUpSlotsByDate(date: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getPickUpSlotsByDate(date).onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
                        it.payload?.let { slots ->
                            _pickUpSlotsForTime.postValue(slots)
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

    fun generatePairOfDate(slots: List<PickUpSlots>) {
        val trios = ArrayList<Triple<String, String, String>>()

        slots.filter { it.available == true }.forEach { slot ->
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
            slot.date?.let { inputDateFormat.parse(it) }?.let { date ->
                val dayOfWeek = outputDateFormat.format(date).substringBefore(", ")
                val dateOfMonth = outputDateFormat.format(date).substringAfter(", ")
                val trio = Triple(dayOfWeek, dateOfMonth, slot.date?.substringBefore("-")?:"")
                if (!trios.contains(trio))
                    trios.add(trio)
            }
        }

        slotDateAdapter.updateDateList(trios)
    }
}