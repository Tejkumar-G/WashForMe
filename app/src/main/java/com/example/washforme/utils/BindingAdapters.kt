package com.example.washforme.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.washforme.R
import com.example.washforme.view.CategoryAdapter

@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView?, adapter: CategoryAdapter?) {
    Log.d("BindingAdapter", "RecyclerView: $recyclerView, Adapter: $adapter")
    if (recyclerView != null && adapter != null) {
        recyclerView.adapter = adapter
    }
}

@BindingAdapter("setImgSrc")
fun ImageView.setImage(url: String?) {
    this.load(url) {
        placeholder(R.drawable.ic_shirt)
        error(R.drawable.ic_shirt)
    }
}

@BindingAdapter("setBackgroundTint")
fun ImageView.setBackgroundTint(@ColorInt colorRes: Int?) {
    if (colorRes!=null)
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(colorRes))
}