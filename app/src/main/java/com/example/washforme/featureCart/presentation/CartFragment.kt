package com.example.washforme.featureCart.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.washforme.R
import com.example.washforme.core.domain.model.ToolbarModel
import com.example.washforme.core.presentation.main.CoreViewModel
import com.example.washforme.databinding.FragmentCartBinding
import com.example.washforme.utils.toWashCategoryRelationList
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    private val viewModel: CoreViewModel by activityViewModels()
    private lateinit var cartAdapter: CartCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cartAdapter = CartCategoryAdapter()
        binding = FragmentCartBinding.inflate(LayoutInflater.from(context), container, false)
        binding.apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
            header = ToolbarModel("Cart Details")
            cartRecycler.adapter = cartAdapter

            pickUpButton.setOnClickListener {
                val json = Gson().toJson(viewModel.currentCart.value)
                findNavController().navigate(
                    R.id.action_mainFragment_to_pickUpSlotFragment,
                    bundleOf("cart" to json)
                )
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter.setChangeInObject { category, item, _ ->
            category?.let { viewModel.setSelectedCategory(it) }
            viewModel.changeInTheObject(item)
            viewModel.getCart()
        }

        lifecycleScope.launch {
            viewModel.currentCart.collectLatest {
                Log.d("totalPriceTag", "onViewCreated: working")
                val cart = it.toWashCategoryRelationList()
                cartAdapter.setCartData(cart)
            }
        }


        /*lifecycleScope.launchWhenCreated {
            viewModel.currentCart.collect {
                Log.d("totalPriceTag", "onViewCreated: working")
                val cart = it.toWashCategoryRelationList()
                cartAdapter.setCartData(cart)
            }
        }*/
    }
}