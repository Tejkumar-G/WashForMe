package com.example.washforme.featureBooking.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.washforme.core.domain.model.ToolbarModel
import com.example.washforme.databinding.FragmentSlotBookBinding
import com.example.washforme.featureBooking.domain.models.PickUpSlots
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SlotBookFragment : Fragment() {

    lateinit var binding: FragmentSlotBookBinding
    private val slotsViewModel: SlotViewModel by viewModels()

    private lateinit var slotDateAdapter: SlotDateAdapter
    private lateinit var slotTimeAdapter: SlotTimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        slotDateAdapter = SlotDateAdapter { date ->
//            slotsViewModel.getSlotsByDate(date)
        }

        slotTimeAdapter = SlotTimeAdapter()

        binding = FragmentSlotBookBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            model = slotsViewModel
            header = ToolbarModel("Book Your Slot", true)
            pickDateRecycler.adapter = slotDateAdapter
            pickTimeRecycler.adapter = slotTimeAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            slotsViewModel.apply {
                launch {
                    pickupSlots.collectLatest {
                        slotDateAdapter.setSlots(it)
                    }
                }
                launch {
                    pickupSlotsTiming.collectLatest {
                        slotTimeAdapter.addSlots(it)
                    }
                }
                launch {
                    toast.collectLatest {
                        showToast(it)
                    }
                }
            }
        }

        binding.apply {
            toolbarLayout.backButton.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun List<PickUpSlots>.getTriples(): ArrayList<Triple<String, String, String>> {
        val trios = ArrayList<Triple<String, String, String>>()

        this.filter { it.available == true }.forEach { slot ->
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
            slot.date?.let { inputDateFormat.parse(it) }?.let { date ->
                val dayOfWeek = outputDateFormat.format(date).substringBefore(", ")
                val dateOfMonth = outputDateFormat.format(date).substringAfter(", ")
                val trio = Triple(dayOfWeek, dateOfMonth, slot.date?.substringBefore("-") ?: "")
                if (!trios.contains(trio))
                    trios.add(trio)
            }
        }
        return trios
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.getPickUpSlots()
//
//        viewModel.pickUpSlots.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                viewModel.generatePairOfDate(it)
//            }
//
//        }
//
//        viewModel.pickUpSlotsForTime.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                viewModel.slotTimeAdapter.addSlots(it)
//            }
//        }
//    }

}