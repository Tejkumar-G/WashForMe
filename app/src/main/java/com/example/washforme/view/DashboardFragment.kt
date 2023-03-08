package com.example.washforme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.washforme.databinding.FragmentDashboardBinding
import com.example.washforme.viewModel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var binding: FragmentDashboardBinding? = null

    val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false).apply {
            model = viewModel
        }
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getCategories()
        viewModel.listOfCategories.observe(viewLifecycleOwner) {
            viewModel.categoryAdapter.addData(it)
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            viewModel.writeToast(requireContext(), it)
        }
    }

    override fun onDestroyView() {
        binding?.unbind()
        binding = null
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        binding?.categoryRV?.apply {
            val gridlayout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            binding?.categoryRV?.layoutManager = gridlayout
        }
    }
}