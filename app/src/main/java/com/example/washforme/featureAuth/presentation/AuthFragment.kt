package com.example.washforme.featureAuth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.washforme.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false).apply {
            model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        handleBackPressEvent()




        return binding.root
    }

    private fun handleBackPressEvent() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(isEnabled) {
//                    if(viewModel._isOTPViewVisible.value) {
//                        isEnabled = false
//
//                    } else {
//                        isEnabled = false
//                        requireActivity().onBackPressed()
//                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            val view2 = binding.phoneNumberContainer
            val view1 = binding.phoneVerificationContainer

            override fun onGlobalLayout() {
                // Remove the listener to avoid multiple calls
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Obtain the view's height
                val viewHeight = view1.height

                // Slide in animation for view1
//                view1.animate().translationX(0f).setDuration(5000).start()

                binding.senOTPBtn.setOnClickListener {
//                    // Slide out animation for view2
//                    view2.animate().translationX(-viewHeight.toFloat()).setDuration(5000)
//                        .withEndAction {
//                            view2.visibility = View.GONE
//                        }.start()
                    SlideAnimation().invoke(binding.phoneNumberContainer, SlideAnimation.AnimationEnum.SLIDE_OUT)
                }


            }
        })

//        SlideAnimation().invoke(binding.phoneNumberContainer, SlideAnimation.AnimationEnum.SLIDE_OUT)
//        SlideAnimation().invoke(binding.phoneNumberContainer, SlideAnimation.AnimationEnum.SLIDE_IN)

    }
}