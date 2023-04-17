package com.example.washforme.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.washforme.R
import com.example.washforme.db.MockRepository
import com.example.washforme.db.ResponseData
import com.example.washforme.utils.MyPreferenceManager
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginViewModelTest {

    lateinit var viewModel : LoginViewModel
    lateinit var pref : MyPreferenceManager
    lateinit var mockRepo : MockRepository

    @Before
    fun setup() {
        pref = mock(MyPreferenceManager::class.java)
        mockRepo = mock(MockRepository::class.java)
        viewModel = LoginViewModel(mockRepo, pref)
    }

    @Test
    fun testValidatePhone() = runBlocking {
        val mockView = mock(View::class.java)
        val phone = "9123540014"

        whenever(mockRepo.validatePhone(phone)).thenReturn(MutableLiveData())

        viewModel.validatePhone(mockView)

        verify(pref).set("Phone", phone)

        verify(mockRepo).validatePhone(phone)

        assertTrue(viewModel.isLoading.value as Boolean)

        val successResponse = ResponseData.success(true)
        whenever(mockRepo.validatePhone(anyString())).thenReturn(MutableLiveData(successResponse))

        assertFalse(viewModel.isLoading.value as Boolean)

        verify(mockView).findNavController().navigate(R.id.loginToOtpFragment)

        val failureResponse = ResponseData.failure("Phone number is invalid", null)
        whenever(mockRepo.validatePhone(phone)).thenReturn(MutableLiveData(failureResponse))

        assertEquals("Phone number is invalid", viewModel.toast.value)

        verify(pref).removeItem("Phone")
    }
}