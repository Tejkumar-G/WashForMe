package com.example.washforme.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.washforme.view.CartFragment
import com.example.washforme.view.DashboardFragment
import com.example.washforme.view.OrdersFragment
import com.example.washforme.view.SettingsFragment
import com.example.washforme.viewModel.MainViewModel

class ViewPagerAdapter(fa: FragmentActivity, val viewModel: MainViewModel) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->DashboardFragment(viewModel)
            1 -> CartFragment(viewModel)
            2 -> OrdersFragment(viewModel)
            3 -> SettingsFragment(viewModel)
            else -> SettingsFragment(viewModel)
        }
    }
}