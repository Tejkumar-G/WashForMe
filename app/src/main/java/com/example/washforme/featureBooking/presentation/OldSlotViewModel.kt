package com.example.washforme.featureBooking.presentation

import androidx.lifecycle.ViewModel
import com.example.washforme.core.data.dataSource.local.preferences.PreferenceManagerImpl
import com.example.washforme.core.data.dataSource.remote.Repository
import com.example.washforme.featureBooking.domain.models.PickUpSlots
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OldSlotViewModel@Inject constructor(
    private val repo: Repository,
    private val pref: PreferenceManagerImpl
): ViewModel() {
//
//    val isLoading = MutableLiveData(false)
//    private val _pickUpSlots: MutableLiveData<List<PickUpSlots>> = MutableLiveData()
//    val pickUpSlots: LiveData<List<PickUpSlots>> = _pickUpSlots
//
//    private val _pickUpSlotsForTime: MutableLiveData<List<PickUpSlots>> = MutableLiveData()
//    val pickUpSlotsForTime: LiveData<List<PickUpSlots>> = _pickUpSlotsForTime
//
//    val isSameAddress : MutableLiveData<Boolean> = MutableLiveData(false)
//
//    val slotDateAdapter = SlotDateAdapter(listOf(), this)
//
//    val slotTimeAdapter = SlotTimeAdapter(listOf(), this)
//
//    fun sameAddressCheckStatusChanged(isChecked: Boolean) {
//        isSameAddress.value = isChecked
//        Log.d("isChecked", "sameAddressCheckStatusChanged: $isChecked")
//    }
//    fun getPickUpSlots() {
//        viewModelScope.launch(Dispatchers.Default) {
//            repo.getPickUpSlots().onEach {
//                when (it.status) {
//                    Status.SUCCESS -> {
//                        isLoading.postValue(false)
//                        it.payload?.let { slots ->
//                            _pickUpSlots.postValue(slots)
//                        }
//                    }
//                    Status.FAILURE -> {
//                        isLoading.postValue(false)
//                    }
//                    Status.LOADING -> {
//                        isLoading.postValue(true)
//                    }
//                }
//            }.launchIn(this)
//        }
//    }
//
//    fun getPickUpSlotsByDate(date: String) {
//        viewModelScope.launch(Dispatchers.Default) {
//            repo.getPickUpSlotsByDate(date).onEach {
//                when (it.status) {
//                    Status.SUCCESS -> {
//                        isLoading.postValue(false)
//                        it.payload?.let { slots ->
//                            _pickUpSlotsForTime.postValue(slots)
//                        }
//
//                    }
//                    Status.FAILURE -> {
//                        isLoading.postValue(false)
//                    }
//                    Status.LOADING -> {
//                        isLoading.postValue(true)
//                    }
//                }
//            }.launchIn(this)
//        }
//    }
//
    fun generatePairOfDate(slots: List<PickUpSlots>): ArrayList<Triple<String, String, String>> {
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

        return trios
    }
}