package com.example.washforme.viewModel

import android.content.Context
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.washforme.adapter.CartCategoryAdapter
import com.example.washforme.adapter.CategoryAdapter
import com.example.washforme.adapter.ItemAdapter
import com.example.washforme.databinding.FragmentDashboardBinding
import com.example.washforme.db.Repository
import com.example.washforme.db.Status
import com.example.washforme.model.Category
import com.example.washforme.model.UserAddress
import com.example.washforme.model.WashCategoryRelation
import com.example.washforme.model.WashItem
import com.example.washforme.roomdb.Cart
import com.example.washforme.roomdb.CartDao
import com.example.washforme.utils.CalledFrom
import com.example.washforme.utils.Constants
import com.example.washforme.utils.ItemCount
import com.example.washforme.utils.MyPreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repository,
    private val pref: MyPreferenceManager
) : ViewModel() {

    val toast = MutableLiveData<String?>()
    private val isLoading = MutableLiveData(false)

    private val _listOfCategories: MutableLiveData<List<Category>?> = MutableLiveData()
    val listOfCategories: LiveData<List<Category>?> = _listOfCategories

    private val _categoryItems: MutableLiveData<List<WashItem>?> = MutableLiveData()
    val categoryItems: LiveData<List<WashItem>?> = _categoryItems

    val categoryAdapter = CategoryAdapter(arrayListOf(), this)
    val itemsAdapter = ItemAdapter(arrayListOf(), this)

    val cartAdapter = CartCategoryAdapter(arrayListOf(), this)


    private val _currentCart: MutableLiveData<ArrayList<WashCategoryRelation>?> = MutableLiveData(
        ArrayList()
    )
    val currentCart: LiveData<ArrayList<WashCategoryRelation>?> = _currentCart

    var noOfItems = MutableLiveData(0)
    var totalPrice = MutableLiveData(0)

    private val _selectedItem = MutableLiveData<MenuItem>()
    val selectedItem = _selectedItem

    lateinit var dbHelper: CartDao

    private val _darkMode: MutableLiveData<Boolean> = MutableLiveData(null)
    var darkMode: MutableLiveData<Boolean> = _darkMode


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
                    when (response.status) {
                        Status.SUCCESS -> {
                            isLoading.postValue(false)
                            view.findNavController()
                                .navigate(com.example.washforme.R.id.dashboardToLoginFragment)
                            view.findNavController()
                                .clearBackStack(com.example.washforme.R.id.loginFragment)
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
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
                        _listOfCategories.postValue(it.payload)
//                        categoryAdapter.notifyDataSetChanged()
                    }
                    Status.FAILURE -> {
                        isLoading.postValue(false)
                        toast.postValue(it.message.toString())
                    }
                    Status.LOADING -> {
                        isLoading.postValue(true)
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getWashingItems(int: Int? = null) {
        viewModelScope.launch(Dispatchers.Default) {
            repo.getWashingItems(int).onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
                        _categoryItems.postValue(it.payload)
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

    fun changeInTheObject(
        option: ItemCount,
        item: WashItem,
        currentCategoryId: Int,
        calledFrom: CalledFrom
    ) {
        if (calledFrom == CalledFrom.DASHBOARD) {
            cartAdapter.cartData = ArrayList()
        } else if (calledFrom == CalledFrom.CART) {
            itemsAdapter.canRefresh = true
        }

        val temporaryCart = _currentCart.value
        val category = getIdOfCategory(currentCategoryId)
        when (option) {
            ItemCount.Add -> {
                temporaryCart?.size?.let {
                    val pos = getCart(temporaryCart, category)
                    if (pos != -1) {
                        val cart = addToCart(temporaryCart[pos], item)
                        temporaryCart[pos] = cart
                    } else {
                        temporaryCart.add(
                            WashCategoryRelation(
                                category,
                                arrayListOf(item)
                            )
                        )
                    }
                }
            }

            ItemCount.Remove -> {
                temporaryCart?.size?.let {
                    val pos = getCart(temporaryCart, category)
                    if (pos != -1) {
                        Log.v("item count", item.count.toString())
                        val cart = removeFromCart(temporaryCart[pos], item)
                        if (cart.items.isEmpty())
                            temporaryCart.removeAt(pos)
                        else
                            temporaryCart[pos] = cart
                    }
                }
            }
        }
        if (temporaryCart != null) {
            val listOfCart = arrayListOf<Cart>()
            dbHelper.deleteAll()

            temporaryCart.forEach {
                listOfCart.add(
                    Cart(
                        category = Gson().toJson(it.category),
                        items = Gson().toJson(it.items)
                    )
                )
            }
            dbHelper.insertAll(listOfCart)
        }
        _currentCart.postValue(temporaryCart)


    }

    private fun removeFromCart(cart: WashCategoryRelation, item: WashItem): WashCategoryRelation {
        var removedPosition = -1
        cart.items.forEach {
            if (it.id == item.id) {
                it.count = item.count
                if (it.count == 0)
                    removedPosition = cart.items.indexOf(it)
            }
        }
        if (removedPosition != -1)
            cart.items.removeAt(removedPosition)
        return cart
    }

    private fun addToCart(cart: WashCategoryRelation, item: WashItem): WashCategoryRelation {
        var isExists = false
        cart.items.forEach {
            if (it.id == item.id) {
                isExists = true
                it.count = item.count
            }
        }
        return if (isExists) {
            cart
        } else {
            cart.items.add(item)
            cart
        }
    }

    private fun getCart(temporaryCart: ArrayList<WashCategoryRelation>, category: Category?): Int {
        temporaryCart.forEach {
            if (it.category?.id == category?.id)
                return temporaryCart.indexOf(it)
        }
        return -1
    }


    fun updateItems(id: Int?) {
        id?.let {
            getWashingItems(id)
            itemsAdapter.updateCurrentCategoryId(id)
        }
    }

    fun updateCountAndPrice() {
        var tempNoOfItems = 0
        var tempTotalPrice = 0
        _currentCart.value?.forEach { washCategoryRelation ->
            washCategoryRelation.items.forEach { item ->
                tempNoOfItems += item.count
                tempTotalPrice += item.count * item.price
                noOfItems.postValue(tempNoOfItems)
                totalPrice.postValue(tempTotalPrice)
            }
        }
    }

    private fun getIdOfItem(id: Int?): WashItem? {
        _categoryItems.value?.forEach { item ->
            if (item.id == id)
                return item
        }
        return null
    }

    fun getIdOfCategory(id: Int?): Category? {
        _listOfCategories.value?.forEach { category ->
            if (category.id == id)
                return category
        }
        return null
    }

    fun onSelectedBottomNavigation(item: MenuItem): Boolean {
        _selectedItem.postValue(item)
        return true
    }

    fun checkForLatestCart() {
        val listOfCart = ArrayList<WashCategoryRelation>()
        dbHelper.getAll().forEach { cart ->
            val itemsJson = cart.items
            val type = object : TypeToken<ArrayList<WashItem>>() {}.type

            listOfCart.add(
                WashCategoryRelation(
                    category = Gson().fromJson(cart.category, Category::class.java),
                    items = Gson().fromJson(itemsJson, type)
                )
            )
        }
        _currentCart.postValue(listOfCart)
    }

    fun createUserWashRelation() {
        viewModelScope.launch(Dispatchers.Default) {
            repo.createUserWashRelation(_currentCart.value).onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        isLoading.postValue(false)
//                        _listOfCategories.postValue(it.payload)
//                        categoryAdapter.notifyDataSetChanged()
                    }
                    Status.FAILURE -> {
                        isLoading.postValue(false)
                        toast.postValue(it.message.toString())
                    }
                    Status.LOADING -> {
                        isLoading.postValue(true)
                    }
                }
            }.launchIn(this)
        }
    }

     fun onBackPressed() {

    }

    fun onSwitchChange(isChecked: Boolean) {
        _darkMode.postValue(isChecked)
    }

    fun updateAddress(binding: FragmentDashboardBinding, userAddress: UserAddress?) {
        val userAddressFromPreference =
            Gson().fromJson(pref.getString(Constants.CURRENT_ADDRESS), UserAddress::class.java)
        if (userAddress == null || userAddress.id != userAddressFromPreference.id) {
            binding.userAddress = userAddressFromPreference
            binding.executePendingBindings()
        }


    }




}