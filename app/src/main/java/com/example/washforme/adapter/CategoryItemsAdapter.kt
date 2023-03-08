package com.example.washforme.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.R
import com.example.washforme.databinding.CategoryItemsHolderBinding
import com.example.washforme.model.ItemsCardColors
import com.example.washforme.model.WashingItems

class CategoryItemsAdapter: RecyclerView.Adapter<CategoryItemsAdapter.ItemHolder>() {

    private var items: List<WashingItems> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsList(items: List<WashingItems>) {
        this.items = items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(
            CategoryItemsHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount() = items.size


    inner class ItemHolder(private val binding: CategoryItemsHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(position: Int) {
            when (position) {
                0 -> binding.color = colors[0]
                1 -> binding.color = colors[1]
                2 -> binding.color = colors[2]
                3 -> binding.color = colors[3]
            }
            binding.items = items[position]
        }
    }

    companion object {
        @SuppressLint("ResourceAsColor")
        private val colors = listOf(
            ItemsCardColors(R.color.card_background_1, R.color.card_light_background_1),
            ItemsCardColors(R.color.card_background_2, R.color.card_light_background_2),
            ItemsCardColors(R.color.card_background_3, R.color.card_light_background_3),
            ItemsCardColors(R.color.card_background_4, R.color.card_light_background_4),
        )
    }
}