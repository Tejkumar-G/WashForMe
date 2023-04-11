package com.example.washforme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.washforme.databinding.FragmentOrdersBinding
import com.example.washforme.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment(viewModel: MainViewModel) : Fragment() {
  lateinit var binding: FragmentOrdersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = FragmentOrdersBinding.inflate(LayoutInflater.from(context), container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            headerName = "Orders"
        }
        return binding.root
    }


}