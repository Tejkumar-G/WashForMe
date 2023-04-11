package com.example.washforme.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.R
import com.example.washforme.databinding.WashItemBinding
import com.example.washforme.model.ItemCardColor
import com.example.washforme.model.WashItem
import com.example.washforme.utils.CalledFrom
import com.example.washforme.utils.ItemCount
import com.example.washforme.viewModel.MainViewModel

class ItemAdapter(var items: List<WashItem>, val viewModel: MainViewModel) :
    RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

    var currentCategoryId = -1
    var canRefresh = false


    fun setItemsList(items: List<WashItem>) {
        if (items.isNotEmpty()) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    fun updateCurrentCategoryId(id: Int){
        this.currentCategoryId = id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(
            WashItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(position, holder.itemView.context)
    }

    override fun getItemCount() = items.size
    fun refresh() {
        notifyDataSetChanged()
    }


    inner class ItemHolder(private val binding: WashItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(position: Int, context: Context) {
            items[position].price += viewModel.getIdOfCategory(currentCategoryId)?.extra_per_item?:0
            binding.item = items[position]
            val colorIndex = position % colors.size
            setColor(colorIndex, context)
            performItemFunctionality(position)

        }

        private fun performItemFunctionality(position: Int) {

            changeCount(position)

            binding.btnAdd.setOnClickListener {
                items[position].count += 1
                viewModel.changeInTheObject(ItemCount.Add, items[position], currentCategoryId, CalledFrom.DASHBOARD)
                changeCount(position)
            }

            binding.addItem.setOnClickListener {
                items[position].count += 1
                viewModel.changeInTheObject(ItemCount.Add, items[position], currentCategoryId, CalledFrom.DASHBOARD)
                changeCount(position)
            }
            binding.removeItem.setOnClickListener {
                if (items[position].count == 0)
                    return@setOnClickListener
                Log.v("item count before", items[position].count.toString())
                items[position].count -= 1
                Log.v("item count after", items[position].count.toString())
                viewModel.changeInTheObject(ItemCount.Remove, items[position], currentCategoryId, CalledFrom.DASHBOARD)
                changeCount(position)
            }
        }

        private fun changeCount(position: Int) {
            viewModel.currentCart.value?.forEach {
                if (it.category?.id == currentCategoryId) {
                    it.items.forEach { item ->
                        if (item.id == items[position].id) {
                            items[position].count = item.count
                        }
                    }
                }
            }
            if (items[position].count > 0) {
                binding.itemCount.text = items[position].count.toString()
                binding.btnAdd.visibility = View.GONE
                binding.btnAddAndRemove.visibility = View.VISIBLE
            } else {
                binding.btnAdd.visibility = View.VISIBLE
                binding.btnAddAndRemove.visibility = View.GONE
            }
        }


        private fun setColor(pos: Int, context: Context) {
            binding.itemCard.setCardBackgroundColor(context.getColor(colors[pos].primary))
            val drawable = ContextCompat.getDrawable(context, R.drawable.card_icon_background)
            drawable?.setTint(ContextCompat.getColor(context, colors[pos].secondary))
            binding.iconBackground.setImageDrawable(drawable)
            binding.btnAddAndRemove.setCardBackgroundColor(context.getColor(R.color.dark_background))
        }
    }

    companion object {

        private val colors = listOf(
            ItemCardColor(R.color.light_pink, R.color.light_background_pink),
            ItemCardColor(R.color.light_purple, R.color.light_background_purple),
            ItemCardColor(R.color.light_orange, R.color.light_background_orange),
            ItemCardColor(R.color.light_blue, R.color.light_background_blue),
        )
    }
}