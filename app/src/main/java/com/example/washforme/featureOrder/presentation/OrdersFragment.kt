package com.example.washforme.featureOrder.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.washforme.core.domain.model.ToolbarModel
import com.example.washforme.core.presentation.main.MainViewModel
import com.example.washforme.databinding.FragmentOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {
  lateinit var binding: FragmentOrdersBinding
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = FragmentOrdersBinding.inflate(LayoutInflater.from(context), container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            header = ToolbarModel("Orders")
        }
        return binding.root
    }


}