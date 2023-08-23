package com.example.washforme.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.washforme.core.data.dataSource.local.room.Cart
import com.example.washforme.core.domain.model.WashCategoryRelation
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.core.domain.model.WashItemResponseModelItem
import com.example.washforme.featureAuth.utils.AuthConstants
import com.google.gson.Gson
import java.util.concurrent.atomic.AtomicBoolean

fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Any>(key)

fun Fragment.setNavigationResult(result: Any, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun String.isPhoneNumber(): Boolean =
    isNotEmpty() && matches(AuthConstants.PHONE_REGEX.toRegex())

fun Any.toJson(): String = Gson().toJson(this)
fun Fragment.showToast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun List<Cart>.toWashCategoryRelationList(): List<WashCategoryRelation> {
    val categoryRelationMap = mutableMapOf<WashCategoryResponseModelItem?, ArrayList<WashItemResponseModelItem>>()

    for (cart in this) {
        val category = cart.category
        val item = cart.items

        if (category != null && item != null) {
            if (!categoryRelationMap.containsKey(category)) {
                categoryRelationMap[category] = ArrayList()
            }
            categoryRelationMap[category]?.add(item)
        }
    }

    return categoryRelationMap.entries.map { (category, items) ->
        WashCategoryRelation(category, items)
    }
}

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    fun call() {
        value = null
    }
}