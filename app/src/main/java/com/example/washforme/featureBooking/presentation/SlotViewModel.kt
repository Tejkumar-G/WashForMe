package com.example.washforme.featureBooking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washforme.featureBooking.domain.models.PickUpSlotsModel
import com.example.washforme.featureBooking.domain.models.Timeslot
import com.example.washforme.featureBooking.domain.useCase.GetSlotsByDateUseCase
import com.example.washforme.featureBooking.domain.useCase.GetSlotsUseCase
import com.example.washforme.utils.baseViewModel.ViewModelUtils
import com.example.washforme.utils.baseViewModel.ViewModelUtilsImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SlotViewModel @Inject constructor(
    private val getSlotsUseCase: GetSlotsUseCase,
    private val getSlotsByDateUseCase: GetSlotsByDateUseCase
): ViewModel(), ViewModelUtils by ViewModelUtilsImpl() {

    init {
        getSlots()
    }

    val isSameAddress = MutableStateFlow(false)

    private val _pickupSlots = MutableStateFlow(listOf<Triple<String, String, String>>())
    val pickupSlots = _pickupSlots.asStateFlow()

    private val _pickupSlotsTiming = MutableStateFlow(listOf<Timeslot>())
    val pickupSlotsTiming = _pickupSlotsTiming.asStateFlow()

    fun getSlots() {
        viewModelScope.launch {
            showLoader(true)
            val response = getSlotsUseCase()
            if(response.isSuccess()) {
                _pickupSlots.emit(generatePairOfDate(response.payload!!))
            } else {
                showToast(response.message.toString())
            }
            showLoader(false)
        }
    }

//    fun getSlotsByDate(date: String) {
//        viewModelScope.launch {
//            showLoader(true)
//            val response = getSlotsByDateUseCase(date)
//            if(response.isSuccess()) {
//                _pickupSlotsTiming.emit(response.payload!!)
//            } else {
//                showToast(response.message.toString())
//            }
//            showLoader(false)
//        }
//    }

    private fun generatePairOfDate(slots: List<PickUpSlotsModel>): ArrayList<Triple<String, String, String>> {
        val trios = ArrayList<Triple<String, String, String>>()

        slots.forEach { slot ->
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
            slot.date.let { inputDateFormat.parse(it) }?.let { date ->
                val dayOfWeek = outputDateFormat.format(date).substringBefore(", ")
                val dateOfMonth = outputDateFormat.format(date).substringAfter(", ")
                val trio = Triple(dayOfWeek, dateOfMonth, slot.date.substringBefore("-")?:"")
                if (!trios.contains(trio))
                    trios.add(trio)
            }
        }

        return trios
    }
}