package com.example.washforme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.washforme.R
import com.example.washforme.databinding.FragmentCartBinding
import com.example.washforme.viewModel.MainViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment(val viewModel: MainViewModel) : Fragment() {
    lateinit var binding: FragmentCartBinding
//    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(context), container, false)
        binding.apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
            headerName = "Cart Details"
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.currentCart.observe(viewLifecycleOwner) {
            if (viewModel.cartAdapter.cartData.isEmpty())
                viewModel.cartAdapter.updateCart(it)
        }

        viewModel.slotBookSingleEvent.observe(viewLifecycleOwner) {
            val json = Gson().toJson(viewModel.currentCart.value)

            findNavController().navigate(R.id.action_mainFragment_to_pickUpSlotFragment, bundleOf("cart" to json))
        }

    }

}