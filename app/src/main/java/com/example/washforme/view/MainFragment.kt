package com.example.washforme.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.washforme.R
import com.example.washforme.adapter.ViewPagerAdapter
import com.example.washforme.databinding.FragmentMainBinding
import com.example.washforme.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(LayoutInflater.from(context), container, false)
        binding.apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerAdapter = ViewPagerAdapter(requireActivity(), viewModel)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false


        viewModel.selectedItem.observe(viewLifecycleOwner) {
            when (it.itemId) {
                R.id.navigation_home -> {
                    binding.viewPager.currentItem = 0
                }
                R.id.navigation_cart -> {
                    binding.viewPager.currentItem = 1
                }

                R.id.navigation_orders -> {
                    binding.viewPager.currentItem = 2
                }
                R.id.navigation_settings -> {
                    binding.viewPager.currentItem = 3
                }
            }
        }

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val resultValue = data?.getBooleanExtra("key", false)?:false
                if (resultValue) {
//                    viewModel.updateAddress(binding, binding.userAddress)
                }
            }
        }
    }
}