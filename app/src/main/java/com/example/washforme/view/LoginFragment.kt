package com.example.washforme.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.washforme.R
import com.example.washforme.databinding.FragmentLoginBinding
import com.example.washforme.utils.Constants
import com.example.washforme.utils.MyPreferenceManager
import com.example.washforme.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject lateinit var myPreferenceManager: MyPreferenceManager

    var binding: FragmentLoginBinding? = null

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            model = loginViewModel
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = myPreferenceManager.getString(Constants.TOKEN)
        if (token.isNotEmpty()) {
            if (myPreferenceManager.getString("USER").isEmpty()) {
                loginViewModel.getUser()
            }
            this.findNavController().navigate(R.id.loginToMainFragment)
        } else {
            loginViewModel.toast.observe(viewLifecycleOwner) {
                loginViewModel.writeToast(requireContext(), it)
            }
        }
    }

    override fun onDestroyView() {
        binding?.unbind()
        binding = null
        super.onDestroyView()
    }
}