package com.example.washforme.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
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

@SuppressLint("ResourceAsColor")
@BindingAdapter("setImgBackground")
fun ImageView.setAdapter(@ColorInt color: Int) {
    this.background.setTint(color)
}

@BindingAdapter("setImgSrc")
fun ImageView.setImage(url: String?) {
    this.load(url) {
        placeholder(R.drawable.ic_shirt)
        error(R.drawable.ic_shirt)
    }
}

@SuppressLint("ResourceAsColor")
@BindingAdapter("setCardBackground")
fun CardView.setImageSource(res: Int) {
    this.setCardBackgroundColor(res)
}
