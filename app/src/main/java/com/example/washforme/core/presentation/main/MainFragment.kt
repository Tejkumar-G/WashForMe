package com.example.washforme.core.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.washforme.R
import com.example.washforme.core.presentation.ViewPagerAdapter2
import com.example.washforme.core.presentation.dashboard.DashboardFragment
import com.example.washforme.databinding.FragmentMainBinding
import com.example.washforme.featureCart.presentation.CartFragment
import com.example.washforme.featureOrder.presentation.OrdersFragment
import com.example.washforme.featureSettings.presentation.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    val viewModel: CoreViewModel by activityViewModels()
    private lateinit var viewPagerAdapter: ViewPagerAdapter2

    private val menus = listOf(
        R.id.navigation_home, R.id.navigation_cart, R.id.navigation_orders, R.id.navigation_settings
    )
    val fragments = listOf(
        DashboardFragment(), CartFragment(), OrdersFragment(), SettingsFragment()
    )

    override fun onStart() {
        super.onStart()
        viewModel.checkUserAlreadyLogIn {
            if (!it) {
                findNavController().navigate(R.id.mainToLoginFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            if (category.value.isEmpty()) {
                getCategory()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(LayoutInflater.from(context), container, false)

        viewPagerAdapter = ViewPagerAdapter2(fragments, childFragmentManager, lifecycle)

        binding.apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner

            bottomInclude.bottomNavigation.also {
                it.setOnItemSelectedListener { menu -> moveToFragment(menu) }
                it.selectedItemId = menus[viewPager.currentItem]
            }

            viewPager.apply {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                adapter = viewPagerAdapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewPager.registerOnPageChangeCallback(viewPagerPageChangeCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPagerPageChangeCallback.let { binding.viewPager.unregisterOnPageChangeCallback(it) }
    }

    private val viewPagerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            binding.bottomInclude.bottomNavigation.menu[position].isChecked = true
        }
    }

    private fun moveToFragment(it: MenuItem): Boolean {
        binding.viewPager.currentItem = menus.indexOf(it.itemId)
        return true
    }
}