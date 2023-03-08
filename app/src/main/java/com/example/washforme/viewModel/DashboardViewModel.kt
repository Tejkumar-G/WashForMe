package com.example.washforme.viewModel

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.washforme.R
import com.example.washforme.adapter.CategoryItemsAdapter
import com.example.washforme.db.Repository
import com.example.washforme.db.Status
import com.example.washforme.model.Categories
import com.example.washforme.model.WashingItems
import com.example.washforme.utils.MyPreferenceManager
import com.example.washforme.view.CategoryAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: Repository,
    private val pref: MyPreferenceManager
) : ViewModel() {

    val toast = MutableLiveData<String?>()
    private val isLoading = MutableLiveData(false)

    private val _listOfCategories: MutableLiveData<Array<Categories>?> = MutableLiveData(null)
    val listOfCategories: LiveData<Array<Categories>?> = _listOfCategories

    private val _categoryItems: MutableLiveData<List<WashingItems>?> = MutableLiveData(null)
    val categoryItems: LiveData<List<WashingItems>?> = _categoryItems

    val categoryAdapter = CategoryAdapter(arrayListOf())
    val itemsAdapter = CategoryItemsAdapter()

    fun writeToast(context: Context, message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            toast.value = null
        }
    }

    fun logoutUser(view: View) {
        viewModelScope.launch {
            view.findViewTreeLifecycleOwner()?.let {
                repo.logoutUser().observe(it) { response ->
                    when(response.status) {
                        Status.SUCCESS -> {
                            isLoading.postValue(false)
                            view.findNavController().navigate(R.id.dashboardToLoginFragment)
                            view.findNavController().clearBackStack(R.id.loginFragment)
                        }
                        Status.FAILURE -> {
                            isLoading.postValue(false)
                            toast.value = response.message.toString()
                        }
                        Status.LOADING -> {
                            isLoading.postValue(true)
                        }
                    }
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getCategories().onEach {
                when(it.status) {
                    Status.SUCCESS ->{
                        isLoading.postValue(false)
                        _listOfCategories.postValue(it.payload)
//                        categoryAdapter.notifyDataSetChanged()
                    }
                    Status.FAILURE -> {
                            isLoading.postValue(false)
                            toast.value = it.message.toString()
                    }
                    Status.LOADING -> {
                        isLoading.postValue(true)
                    }
                }
            }.launchIn(this)
        }
    }

    fun getWashingItems() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getWashingItems().onEach {
                when(it.status) {
                    Status.SUCCESS ->{
                        isLoading.postValue(false)
                        _categoryItems.postValue(it.payload)
//                        categoryAdapter.notifyDataSetChanged()
                    }
                    Status.FAILURE -> {
                            isLoading.postValue(false)
                            toast.value = it.message.toString()
                    }
                    Status.LOADING -> {
                        isLoading.postValue(true)
                    }
                }
            }.launchIn(this)
        }
    }
}