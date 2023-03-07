package com.example.washforme.utils

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.view.CategoryAdapter

@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView?, adapter: CategoryAdapter?) {
    Log.d("BindingAdapter", "RecyclerView: $recyclerView, Adapter: $adapter")
    if (recyclerView != null && adapter != null) {
        recyclerView.adapter = adapter
    }
}
