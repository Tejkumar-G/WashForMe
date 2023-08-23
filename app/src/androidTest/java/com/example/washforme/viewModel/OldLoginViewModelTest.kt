package com.example.washforme.viewModel

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class OldLoginViewModelTest {
//
//    lateinit var viewModel : LoginViewModel
//    lateinit var pref : MyPreferenceManager
//
//    @Before
//    fun setup() {
//        pref = mock(MyPreferenceManager::class.java)
//        mockRepo = mock(MockRepository::class.java)
//        viewModel = LoginViewModel(mockRepo, pref)
//    }
//
//    @Test
//    fun testValidatePhone() = runBlocking {
//        val mockView = mock(View::class.java)
//        val phone = "9123540014"
//
//        whenever(mockRepo.validatePhone(phone)).thenReturn(MutableLiveData())
//
//        viewModel.validatePhone(mockView)
//
//        verify(pref).set("Phone", phone)
//
//        verify(mockRepo).validatePhone(phone)
//
//        assertTrue(viewModel.isLoading.value as Boolean)
//
//        val successResponse = ResponseData.success(true)
//        whenever(mockRepo.validatePhone(anyString())).thenReturn(MutableLiveData(successResponse))
//
//        assertFalse(viewModel.isLoading.value as Boolean)
//
//        verify(mockView).findNavController().navigate(R.id.loginToOtpFragment)
//
//        val failureResponse = ResponseData.failure("Phone number is invalid", null)
//        whenever(mockRepo.validatePhone(phone)).thenReturn(MutableLiveData(failureResponse))
//
//        assertEquals("Phone number is invalid", viewModel.toast.value)
//
//        verify(pref).removeItem("Phone")
//    }
}