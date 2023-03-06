package com.example.washforme.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.washforme.R
import com.example.washforme.databinding.FragmentOtpBinding
import com.example.washforme.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {

    var binding : FragmentOtpBinding? = null
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentOtpBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            model = loginViewModel


            loginViewModel.validateOtpResponse.observe(viewLifecycleOwner) {
                if(it.status)
                    findNavController().navigate(R.id.otpFragmentToMainActivity)
            }
            return binding?.root
        }
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