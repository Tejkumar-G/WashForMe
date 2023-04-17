package com.example.washforme.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.washforme.R
import com.example.washforme.utils.Constants
import com.example.washforme.utils.MyPreferenceManager
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginFragmentTest {

    @Test
    fun testIsUserAlreadyLogin() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val pref = mockk<MyPreferenceManager>()
        pref.set(Constants.TOKEN, "abcdefghijklmnopqrstuvwxyz,./")

        val loginFragment = launchFragmentInContainer<LoginFragment>()

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.mainFragment)
    }

    @Test
    fun testIsUserNotRegistered() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val pref = mockk<MyPreferenceManager>()
        val loginFragment = launchFragmentInContainer<LoginFragment>()

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.loginFragment)
    }
}