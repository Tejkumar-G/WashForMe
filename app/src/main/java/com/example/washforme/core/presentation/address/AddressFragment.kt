package com.example.washforme.core.presentation.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.washforme.R
import com.example.washforme.core.domain.model.ToolbarModel
import com.example.washforme.databinding.FragmentAddressBinding
import com.example.washforme.utils.setNavigationResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressFragment : Fragment() {
    lateinit var binding: FragmentAddressBinding
    val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddressBinding.inflate(LayoutInflater.from(context), container, false)
        binding.apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
            header = ToolbarModel("Addresses")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllAddresses()
        binding.topLayout.backButton.visibility = View.VISIBLE
        binding.topLayout.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.allAddresses.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.addressAdapter.storeData(it)
            }
        }

        viewModel.addressChanged.observe(viewLifecycleOwner) {
            if (it) {
                setNavigationResult(result = true)
                viewModel.addressChanged.postValue(false)
            }

        }

        viewModel.addressForEditingSingleEvent.observe(viewLifecycleOwner) {
            it?.let {
                if (it.id==null) {
                    findNavController().navigate(
                        R.id.action_addressFragment_to_editAddressFragment
                    )
                } else {
                    val userAddressGson = Gson().toJson(it)
                    findNavController().navigate(
                        R.id.action_addressFragment_to_editAddressFragment,
                        bundleOf("userAddressGson" to userAddressGson)
                    )
                    viewModel.addressForEditingSingleEvent.call()
                }
            }

        }

    }

    override fun onResume() {
        super.onResume()
        if (viewModel.exitFromScreen.value == true) {
            viewModel.getAllAddresses()
            viewModel.exitFromScreen.value = false
        }

    }


}