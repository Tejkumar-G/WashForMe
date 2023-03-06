package com.example.washforme.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.washforme.databinding.FragmentDashboardBinding
import com.example.washforme.viewModel.LoginViewModel

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false).apply {
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.toast.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                loginViewModel.toast.value = null
            }
        }
    }
}