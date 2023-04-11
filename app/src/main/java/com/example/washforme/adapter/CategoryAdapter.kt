package com.example.washforme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.R
import com.example.washforme.databinding.CategoryItemBinding
import com.example.washforme.model.Category
import com.example.washforme.viewModel.MainViewModel

class CategoryAdapter(
    var categories: List<Category>,
    private val viewModel: MainViewModel
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =

        ViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position, holder.itemView.context)

    }

    override fun getItemCount(): Int = categories.size

    fun addData(categories: List<Category>?) {
        if (categories != null) {
            this.categories = categories
            notifyItemRangeInserted(0, this.categories.size)
        }
    }

    inner class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(position: Int, context: Context) {
            binding.category = categories[position]
            binding.clLayout.setOnClickListener {
                if (selectedPosition == position)
                    return@setOnClickListener
                viewModel.updateItems(categories[position].id)
                val oldPosition = selectedPosition
                selectedPosition = position

                notifyItemChanged(oldPosition)
                notifyItemChanged(position)
            }
            if (position == selectedPosition) {
                binding.clLayout.setBackgroundResource(R.drawable.dark_round)
                binding.name.setTextColor(context.getColor(R.color.primary_white))
            }
            else {
                binding.clLayout.setBackgroundResource(R.drawable.rounded_corner)
                binding.name.setTextColor(context.getColor(R.color.black))
            }
        }

    }
}