package com.example.washforme.featureAuth.presentation.otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.washforme.R
import com.example.washforme.databinding.FragmentOtpBinding
import com.example.washforme.featureAuth.presentation.AuthViewModel
import com.example.washforme.featureAuth.utils.AuthConstants
import com.example.washforme.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private lateinit var binding : FragmentOtpBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var otpDigitsViews: List<EditText>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentOtpBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            model = authViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.apply {

            otpTxtChangeHandler()

            lifecycleScope.launchWhenStarted {
                launch {
                    navigation.collectLatest {
                        when (it) {
                            AuthConstants.NAV_TO_MAIN_FRAGMENT ->
                                findNavController().navigate(R.id.otpFragmentToMainFragment)
                            else -> Unit
                        }
                    }
                }
                launch {
                    toast.collectLatest { showToast(it) }
                }

                launch {
                    _otp.collectLatest {
                        authViewModel._otp.collectLatest { otpDigits ->
                            otpDigitsViews.forEachIndexed { index, editText ->
                                editText.setText(otpDigits.getOrNull(index))
                            }
                        }
                    }
                }
            }
        }

    }

    private fun otpTxtChangeHandler() {

        otpDigitsViews = listOf(
            binding.txtOtp1, binding.txtOtp2, binding.txtOtp3, binding.txtOtp4,
        )

        otpDigitsViews.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    authViewModel.updateOtpDigit(index, s.toString())
                    if(s.isNullOrEmpty()) return
                    if (s.length == 1 && index < 3) {
                        otpDigitsViews[index + 1].requestFocus()
                    }
                }
            })

            editText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (index >= 0) {
                        if(editText.text.isNullOrEmpty()) {
                            val focusIndex = if(index == 0) 0 else index - 1
                            authViewModel.updateOtpDigit(focusIndex, "")
                            otpDigitsViews[focusIndex].requestFocus()
                        } else {
                            authViewModel.updateOtpDigit(index, "")
                        }
                    }
                    return@OnKeyListener true
                }
                false
            })

        }
    }
}