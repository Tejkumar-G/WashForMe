package com.example.washforme.utils

import android.content.res.ColorStateList
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.washforme.R
import com.example.washforme.adapter.*
import com.google.android.material.bottomnavigation.BottomNavigationView

@BindingAdapter("categoryAdapter")
fun adapter1(recyclerView: RecyclerView?, adapter: CategoryAdapter?) {
    Log.d("BindingAdapter", "RecyclerView: $recyclerView, Adapter: $adapter")
    if (recyclerView != null && adapter != null) {
        recyclerView.adapter = adapter
    }
}

@BindingAdapter("itemAdapter")
fun adapter2(recyclerView: RecyclerView?, adapter: ItemAdapter?) {
    Log.d("BindingAdapter", "RecyclerView: $recyclerView, Adapter: $adapter")
    if (recyclerView != null && adapter != null) {
        recyclerView.adapter = adapter
    }
}
@BindingAdapter("cartAdapter")
fun adapter3(recyclerView: RecyclerView?, adapter: CartCategoryAdapter?) {
    Log.d("BindingAdapter", "RecyclerView: $recyclerView, Adapter: $adapter")
    if (recyclerView != null && adapter != null) {
        recyclerView.adapter = adapter
    }
}

@BindingAdapter("cartItemAdapter")
fun adapter4(recyclerView: RecyclerView?, adapter: CartItemAdapter?) {
    Log.d("BindingAdapter", "RecyclerView: $recyclerView, Adapter: $adapter")
    if (recyclerView != null && adapter != null) {
        recyclerView.adapter = adapter
    }
}

@BindingAdapter("addressAdapter")
fun adapter5(recyclerView: RecyclerView?, adapter: AddressAdapter?) {
    if (recyclerView !=null && adapter != null) {
        recyclerView.adapter = adapter
    }
}

@BindingAdapter("selectedItem")
fun setSelectedItem(view: BottomNavigationView, itemId: Int?) {
    if (itemId != null) {
        view.selectedItemId = itemId
    }
}

@BindingAdapter("setImgSrc")
fun ImageView.setImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_shirt)
        .error("https://washcategories.s3.ap-northeast-1.amazonaws.com/media/hoodie.png")
        .into(this);
}

@BindingAdapter("setBackgroundTint")
fun ImageView.setBackgroundTint(@ColorInt colorRes: Int?) {
    if (colorRes!=null)
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(colorRes))
}
