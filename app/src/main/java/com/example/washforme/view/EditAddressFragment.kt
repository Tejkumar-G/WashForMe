package com.example.washforme.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.washforme.R
import com.example.washforme.databinding.FragmentEditAddressBinding
import com.example.washforme.model.UserAddress
import com.example.washforme.viewModel.AddressViewModel
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAddressFragment : Fragment() {
    lateinit var binding: FragmentEditAddressBinding
    val viewModel: AddressViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditAddressBinding.inflate(LayoutInflater.from(context), container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topLayout.backButton.visibility = View.VISIBLE
        binding.topLayout.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.exitFromScreen.observe(viewLifecycleOwner) {
            if (it)
                findNavController().popBackStack()
        }
        val stringJson = arguments?.getString("userAddressGson")



        if (!stringJson.isNullOrEmpty()) {
            val userAddress = Gson().fromJson(stringJson, UserAddress::class.java)
            viewModel.userAddress = userAddress
            binding.userAddress = userAddress
            viewModel.addAddressType(userAddress)
            binding.topLayout.headerName = "Edit Address"
        } else {
            binding.topLayout.headerName = "Add Address"
        }

        binding.chipHome.addDefaultChipDetails()
        binding.chipOffice.addDefaultChipDetails()
        binding.chipAdd.addDefaultChipDetails()

        viewModel.addressType.observe(viewLifecycleOwner) {
            when(it) {
                "home" -> viewModel.changeAddressTypeView(binding.chipHome, binding.chipOffice, binding.chipAdd)
                "office" -> viewModel.changeAddressTypeView(binding.chipOffice, binding.chipHome, binding.chipAdd)
                "other" -> viewModel.changeAddressTypeView(binding.chipAdd, binding.chipOffice, binding.chipHome)
            }
        }




    }
    @SuppressLint("ResourceType")
    fun Chip.addDefaultChipDetails() {
        this.setChipBackgroundColorResource(R.drawable.chip_background)
        this.checkedIcon = null
        this.isCheckable = true
    }

}