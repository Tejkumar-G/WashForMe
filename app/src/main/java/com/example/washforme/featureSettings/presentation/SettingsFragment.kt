package com.example.washforme.featureSettings.presentation

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.washforme.R
import com.example.washforme.core.data.dataSource.local.preferences.PreferenceManagerImpl
import com.example.washforme.core.domain.model.ToolbarModel
import com.example.washforme.core.presentation.main.MainViewModel
import com.example.washforme.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject





@AndroidEntryPoint
class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    private val viewModel: MainViewModel by activityViewModels()

    @Inject lateinit var preferenceManagerImpl: PreferenceManagerImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            activity?.setTheme(R.style.DarkTheme)
        } else {
            activity?.setTheme(R.style.LightTheme)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(LayoutInflater.from(context), container, false)
//        val settings = Gson().fromJson(viewModel.user?.settings, Settings::class.java)
//        viewModel.darkMode.postValue(settings.darkMode != "Yes")

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            header = ToolbarModel("Settings")
            user = viewModel.user
            model = viewModel
            settings = settings
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.darkMode.observe(viewLifecycleOwner) {
            if (it==null)
                return@observe
            val uiManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager?

            if (uiManager != null) {
                uiManager.nightMode =
                    if (it) UiModeManager.MODE_NIGHT_YES else UiModeManager.MODE_NIGHT_NO
                val parentFragmentManager = parentFragmentManager
                val currentFragment = parentFragmentManager.fragments[3]
                parentFragmentManager.beginTransaction()
                    .detach(currentFragment)
                    .attach(currentFragment)
                    .commitAllowingStateLoss()
            }

        }
    }

}
