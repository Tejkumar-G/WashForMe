package com.example.washforme.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.washforme.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("addAdapter")
fun assAdapter(recyclerView: RecyclerView?, adapter: RecyclerView.Adapter<*>?) {
    Log.d("BindingAdapter", "RecyclerView: $recyclerView, Adapter: $adapter")
    if (recyclerView != null && adapter != null) {
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

@BindingAdapter("setDisabled")
fun setViewDisable(view: View, boolean: Boolean) {
    when (view) {
        is TextView -> {
            view.isEnabled = boolean
            view.setTextColor(if (boolean) Color.BLACK else Color.GRAY)
        }
        is ImageView -> {
            view.setBackgroundTint(if(boolean) ContextCompat.getColor(view.context, R.color.button_color) else Color.GRAY)
        }
    }
    view.setBackgroundColor(if (boolean) Color.WHITE else Color.LTGRAY)
}

@BindingAdapter("addTextInTimeFormat")
fun TextView.addTextInTimeFormat(time: String) {
    val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val formattedTime = inputFormat.parse(time)?.let { outputFormat.format(it) }
    this.text = formattedTime
}
