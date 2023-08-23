package com.example.washforme.featureCart.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.core.domain.model.WashCategoryRelation
import com.example.washforme.core.domain.model.WashItemResponseModelItem
import com.example.washforme.databinding.CartItemDetailBinding
import com.example.washforme.featureCart.domain.interfaces.ItemToCategory
import com.example.washforme.utils.CalledFrom

class CartItemAdapter(
    var items: ArrayList<WashItemResponseModelItem>,
    private var cartData: List<WashCategoryRelation>,
    val currentCategoryId: String,
    val itemToCategory: ItemToCategory
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    private var changeInTheObject: ((WashItemResponseModelItem, CalledFrom) -> Unit)? = null

    fun setChangeInObject(changeInTheObject: (WashItemResponseModelItem, CalledFrom) -> Unit) {
        this.changeInTheObject = changeInTheObject
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CartItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: ArrayList<WashItemResponseModelItem>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(private val binding: CartItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: WashItemResponseModelItem) {
            binding.item = item

            binding.addItem.setOnClickListener { _ ->
                item.count += 1
                changeInTheObject?.let { it(item, CalledFrom.CART) }
                binding.itemCount.text = item.count.toString()
            }

            binding.removeItem.setOnClickListener {
                if (item.count > 0)
                    item.count -= 1
                changeInTheObject?.let { it(item, CalledFrom.CART) }
                if (item.count > 0)
                    binding.itemCount.text = item.count.toString()
            }

//        private fun changeCount(item: WashItemResponseModelItem) {
//            cartData.forEach {
//                if (it.category?.id == currentCategoryId) {
//                    it.items.forEach { mItem ->
//                        if (mItem.id == item.id) {
//                            item.count = item.count
//                        }
//                    }
//                }
//            }
//
//            if (item.count == 0) {
//                notifyItemRemoved(absoluteAdapterPosition)
//                if (items.isEmpty()){
//                    itemToCategory.itemsChanged(currentCategoryId, true)
//                }
//            } else {
//                binding.itemCount.text = item.count.toString()
//                itemToCategory.itemsChanged(currentCategoryId, false)
//            }
//        }
        }
    }
}