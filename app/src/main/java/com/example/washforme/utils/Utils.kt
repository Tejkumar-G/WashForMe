package com.example.washforme.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import java.util.concurrent.atomic.AtomicBoolean

fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Any>(key)

fun Fragment.setNavigationResult(result: Any, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
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