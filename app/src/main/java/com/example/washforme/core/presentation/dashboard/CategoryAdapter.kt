package com.example.washforme.core.presentation.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.R
import com.example.washforme.core.domain.model.WashCategoryResponseModel
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.databinding.CategoryItemBinding

class CategoryAdapter(
    val updateItems: (WashCategoryResponseModelItem) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var selectedPosition = 0
    var categories: WashCategoryResponseModel? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCategory(categories: WashCategoryResponseModel) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        categories?.get(position)?.let {
            holder.setData(it)
        }
    }

    override fun getItemCount(): Int = categories?.size ?: 0

//    fun addData(categories: List<Category>?) {
//        if (categories != null) {
//            this.categories = categories
//            notifyItemRangeInserted(0, this.categories.size)
//        }
//    }

    inner class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(category: WashCategoryResponseModelItem) {
            binding.apply {
                categoryName = category.name
                binding.root.setOnClickListener {

                }
            }
            binding.clLayout.setOnClickListener {
                if (selectedPosition == absoluteAdapterPosition)
                    return@setOnClickListener
                updateItems(category)
                val oldPosition = selectedPosition
                selectedPosition = absoluteAdapterPosition

                notifyItemChanged(oldPosition)
                notifyItemChanged(absoluteAdapterPosition)
            }
            if (absoluteAdapterPosition == selectedPosition) {
                binding.clLayout.setBackgroundResource(R.drawable.dark_round)
                binding.name.setTextColor(binding.root.context.getColor(R.color.primary_white))
            } else {
                binding.clLayout.setBackgroundResource(R.drawable.rounded_corner)
                binding.name.setTextColor(binding.root.context.getColor(R.color.black))
            }
        }
    }
}