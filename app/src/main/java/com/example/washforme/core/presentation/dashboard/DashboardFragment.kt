package com.example.washforme.core.presentation.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.washforme.R
import com.example.washforme.core.presentation.main.CoreViewModel
import com.example.washforme.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var washItemAdapter: ItemAdapter
    private val viewModel: CoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false).apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter { category ->
            viewModel.setSelectedCategory(category)
            lifecycleScope.launchWhenCreated {
                launch {
                    viewModel.getWashingItems(category.id)
                    viewModel.washItems.collectLatest {
                        Log.d("washItem", "onViewCreated: $it")
                        washItemAdapter.setWashItems(
                            items = it
                        )
                    }
                }
            }
        }

        washItemAdapter =
            ItemAdapter {item, _ ->
                viewModel.changeInTheObject(item)
            }

        binding.apply {
            categoryRV.adapter = categoryAdapter
            itemsRecycler.adapter = washItemAdapter
        }

        setupRecyclerView()
//        viewModel.
//        AppDatabase.getInstance(requireContext())?.let {
////            viewModel.dbHelper = it.cartDao()
//            viewModel.checkForLatestCart()
//        }


//        viewModel.listOfCategories.observe(viewLifecycleOwner) {
//            viewModel.categoryAdapter.addData(it)
//            viewModel.updateItems(it?.get(0)?.id)
//        }
//
//        viewModel.categoryItems.observe(viewLifecycleOwner) {
//            it?.let { item -> viewModel.itemsAdapter.setItemsList(item) }
//        }

//        viewModel.toast.observe(viewLifecycleOwner) {
//            viewModel.writeToast(requireContext(), it)
//        }

        viewModel.apply {
            lifecycleScope.launchWhenCreated {
                launch {
                    toast.collectLatest { showToast(it) }
                }

                launch {
                    category.collectLatest { categories ->
                        categories.let {
                            categoryAdapter.setCategory(it)
//                            if (it.size != 0) {
//                                washItemAdapter.setWashItems(category = it[0])
//                            }
                        }
                    }
                }

                launch {
                    washItems.collectLatest { washItems ->
                            washItemAdapter.setWashItems(items = washItems)
                    }
                }

//                launch {
//                    currentCart.collectLatest { cart ->
//                        washItemAdapter.setWashItems(currentCart = cart)
//                    }
//                }
            }
        }

//        viewModel.currentCart.observe(viewLifecycleOwner) {
//            if (viewModel.itemsAdapter.canRefresh) {
//                viewModel.itemsAdapter.refresh()
//            }
//            if (it.isNullOrEmpty()) {
//                binding.itemsCard.visibility = View.GONE
//                binding.viewForBottomPadding.visibility = View.GONE
//            } else {
//                binding.itemsCard.visibility = View.VISIBLE
//                binding.viewForBottomPadding.visibility = View.VISIBLE
//                viewModel.updateCountAndPrice()
//            }
//        }

        binding.clAddress.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addressFragment)
        }


//        viewModel.updateAddress(binding, viewModel.user?.defaultAddress, true)
//
//        getNavigationResult()?.observe(viewLifecycleOwner) {
//            if (it == true) {
//                viewModel.updateAddress(binding, viewModel.user?.defaultAddress, false)
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCart(viewModel.currentCategory.value)
    }
    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        binding.categoryRV.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
        binding.itemsRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}