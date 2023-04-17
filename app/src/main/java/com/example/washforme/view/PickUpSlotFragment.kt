package com.example.washforme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.washforme.databinding.FragmentPickUpSlotBinding
import com.example.washforme.viewModel.SlotViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickUpSlotFragment : Fragment() {

    lateinit var binding: FragmentPickUpSlotBinding
    private val viewModel: SlotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickUpSlotBinding.inflate(LayoutInflater.from(context), container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPickUpSlots()

        viewModel.pickUpSlots.observe(viewLifecycleOwner) {

        }
    }

}