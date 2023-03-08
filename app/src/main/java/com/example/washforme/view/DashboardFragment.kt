package com.example.washforme.view

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.washforme.databinding.FragmentDashboardBinding
import com.example.washforme.viewModel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false).apply {
            model = viewModel
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getCategories()
        viewModel.getWashingItems()
        viewModel.listOfCategories.observe(viewLifecycleOwner) {
            viewModel.categoryAdapter.addData(it)
        }

        viewModel.categoryItems.observe(viewLifecycleOwner) {
            it?.let { it1 -> viewModel.itemsAdapter.setItemsList(it1) }
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            viewModel.writeToast(requireContext(), it)
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        binding.categoryRV.apply {
            val gridlayout = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            binding.categoryRV.layoutManager = gridlayout
        }

        binding.itemsRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val position: Int = parent.getChildAdapterPosition(view) // item position

                    val spanCount = 2
                    val spacing = 25 //spacing between views in grid

                    if (position >= 0) {
                        val column = position % spanCount // item column
                        outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                        outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                        if (position < spanCount) { // top edge
                            outRect.top = spacing
                        }
                        outRect.bottom = spacing // item bottom
                    } else {
                        outRect.left = 0
                        outRect.right = 0
                        outRect.top = 0
                        outRect.bottom = 0
                    }
                }
            })
        }
    }
}