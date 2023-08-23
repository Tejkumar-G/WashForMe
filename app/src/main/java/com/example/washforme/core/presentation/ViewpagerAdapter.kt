package com.example.washforme.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.washforme.core.presentation.dashboard.DashboardFragment
import com.example.washforme.featureCart.presentation.CartFragment
import com.example.washforme.featureOrder.presentation.OrdersFragment
import com.example.washforme.featureSettings.presentation.SettingsFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DashboardFragment()
            1 -> CartFragment()
            2 -> OrdersFragment()
            3 -> SettingsFragment()
            else -> SettingsFragment()
        }
    }
}