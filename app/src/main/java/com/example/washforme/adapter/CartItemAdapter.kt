package com.example.washforme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.databinding.CartItemDetailBinding
import com.example.washforme.interfaces.ItemToCategory
import com.example.washforme.model.WashItem
import com.example.washforme.utils.CalledFrom
import com.example.washforme.utils.ItemCount
import com.example.washforme.viewModel.MainViewModel

class CartItemAdapter(
    var items: ArrayList<WashItem>,
    var viewModel: MainViewModel,
    val currentCategoryId: Int,
    val itemToCategory: ItemToCategory
): RecyclerView.Adapter<CartItemAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CartItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int = items.size
    fun updateItems(items: ArrayList<WashItem>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }


    inner class ViewHolder(private val binding: CartItemDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun setData(position: Int){
            binding.item = items[position]
            binding.model = viewModel


            binding.addItem.setOnClickListener {
                items[position].count += 1
                viewModel.changeInTheObject(ItemCount.Add, items[position], currentCategoryId, CalledFrom.CART)
                changeCount(position)
            }
            binding.removeItem.setOnClickListener {
                if (items[position].count == 0)
                    return@setOnClickListener
                items[position].count -= 1
                viewModel.changeInTheObject(ItemCount.Add, items[position], currentCategoryId, CalledFrom.CART)
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
            if (items[position].count == 0) {
                items.remove(items[position])
                notifyItemRemoved(position)
                if (items.isEmpty()){
                    itemToCategory.itemsChanged(currentCategoryId, true)
                }

            } else {
                binding.itemCount.text = items[position].count.toString()
                itemToCategory.itemsChanged(currentCategoryId, false)
            }
        }
    }
}