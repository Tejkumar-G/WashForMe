package com.example.washforme.featureAuth.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.washforme.R
import com.example.washforme.databinding.FragmentLoginBinding
import com.example.washforme.featureAuth.presentation.AuthViewModel
import com.example.washforme.featureAuth.utils.AuthConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            countryCodePicker.apply {
                registerCarrierNumberEditText(txtPhone) // register the text view for using phoneNumberValidityChangeListener

                setPhoneNumberValidityChangeListener { isValidPhone -> // Disable sendOTPBtn when entered phone number is not valid
                    senOTPBtn.isClickable = isValidPhone
                    senOTPBtn.isEnabled = isValidPhone
                }

                setOnCountryChangeListener { // get country code whenever new country code is selected from dialog
                    viewModel.countryCode.value = countryCodePicker.selectedCountryCodeWithPlus
                }
            }
        }

        viewModel.apply {
            lifecycleScope.launchWhenCreated {

                launch {
                    toast.collectLatest {
                        showToast(it)
                    }
                }

                launch {
                    navigation.collectLatest {
                        when (it) {
                            AuthConstants.NAV_TO_OTP_FRAGMENT ->
                                this@LoginFragment.findNavController().navigate(R.id.loginToOtpFragment)
                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}