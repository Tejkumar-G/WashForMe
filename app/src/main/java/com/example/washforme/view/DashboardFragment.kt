package com.example.washforme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.washforme.R
import com.example.washforme.databinding.FragmentDashboardBinding
import com.example.washforme.roomdb.AppDatabase
import com.example.washforme.utils.getNavigationResult
import com.example.washforme.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment(val viewModel: MainViewModel) : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

//    val viewModel: MainViewModel by viewModels()

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
        setupRecyclerView()
        viewModel.getCategories()
        AppDatabase.getInstance(requireContext())?.let {
            viewModel.dbHelper = it.cartDao()
            viewModel.checkForLatestCart()
        }

        viewModel.listOfCategories.observe(viewLifecycleOwner) {
            viewModel.categoryAdapter.addData(it)
            viewModel.updateItems(it?.get(0)?.id)
        }

        viewModel.categoryItems.observe(viewLifecycleOwner) {
            it?.let { item -> viewModel.itemsAdapter.setItemsList(item) }
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            viewModel.writeToast(requireContext(), it)
        }

        viewModel.currentCart.observe(viewLifecycleOwner) {
            if (viewModel.itemsAdapter.canRefresh) {
                viewModel.itemsAdapter.refresh()
            }
            if (it.isNullOrEmpty()) {
                binding.itemsCard.visibility = View.GONE
                binding.viewForBottomPadding.visibility = View.GONE
            } else {
                binding.itemsCard.visibility = View.VISIBLE
                binding.viewForBottomPadding.visibility = View.VISIBLE
                viewModel.updateCountAndPrice()
            }
        }

        binding.clAddress.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addressFragment)
        }


        viewModel.updateAddress(binding, binding.userAddress)

        getNavigationResult()?.observe(viewLifecycleOwner) {
            if (it==true) {
                viewModel.updateAddress(binding, binding.userAddress)
            }
        }

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