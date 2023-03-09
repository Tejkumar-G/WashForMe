package com.example.washforme.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.databinding.CategoryItemsHolderBinding
import com.example.washforme.model.ItemsCardColors
import com.example.washforme.model.WashingItems
import com.example.washforme.utils.setBackgroundTint

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
            when (position % colors.size) {
                0 -> setColor(0)
                1 -> setColor(1)
                2 -> setColor(2)
                3 -> setColor(3)
            }
            binding.items = items[position]
        }

        @SuppressLint("ResourceType")
        private fun setColor(pos: Int) {
            binding.itemCard.setCardBackgroundColor(colors[pos].primary)
            binding.iconBackground.setBackgroundTint(colors[pos].secondary)
        }
    }

    companion object {
        @SuppressLint("ResourceAsColor")
        private val colors = listOf(
            ItemsCardColors(Color.parseColor("#FFE4E4"), Color.parseColor("#FFF0F1")),
            ItemsCardColors(Color.parseColor("#F0E2FC"), Color.parseColor("#F9ECFF")),
            ItemsCardColors(Color.parseColor("#FFDCBB"), Color.parseColor("#FFF5E7")),
            ItemsCardColors(Color.parseColor("#D7F8FF"), Color.parseColor("#ECFCFF")),
        )
    }
}