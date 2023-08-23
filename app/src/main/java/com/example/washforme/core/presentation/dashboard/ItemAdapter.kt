package com.example.washforme.core.presentation.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.R
import com.example.washforme.core.domain.model.ItemCardColor
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.core.domain.model.WashItemResponseModel
import com.example.washforme.core.domain.model.WashItemResponseModelItem
import com.example.washforme.databinding.WashItemBinding
import com.example.washforme.utils.CalledFrom

class ItemAdapter(
    val changeInTheObject: (
        item: WashItemResponseModelItem,
        calledFrom: CalledFrom
    ) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

    var items = WashItemResponseModel()
    var category: WashCategoryResponseModelItem? = null

    fun setWashItems(items: WashItemResponseModel) {
        this.items = items
//        notifyItemRangeChanged(0, items.size)
        notifyDataSetChanged()
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
        items[position].let { holder.setData(it) }
    }

    override fun getItemCount() = items.size

    inner class ItemHolder(private val binding: WashItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: WashItemResponseModelItem) {
            val price =
                category?.extra_per_item?.let { (it.toDouble() + item.price.toDouble()).toString() }
                    ?: item.price
            binding.also {
                it.item = item
                it.price = price
            }
            val colorIndex = absoluteAdapterPosition % colors.size
            setColor(colorIndex, binding.root.context)
//            checkItemAlreadyAvailable(item)
            changeCount(item)
            performItemFunctionality(item)

        }
//
//        private fun checkItemAlreadyAvailable(item: WashItemResponseModelItem) {
//            currentCart.forEach { cart ->
//                if (cart.category?.id == category?.id) {
//                    item.count = cart.items.find { cItem -> cItem.id == item.id }?.count ?: 0
//                } else {
//                    item.count = 0
//                }
//            }
//            changeCount(item)
//        }

        private fun performItemFunctionality(item: WashItemResponseModelItem) {

            binding.btnAdd.setOnClickListener {
                item.count += 1
                changeInTheObject(item, CalledFrom.DASHBOARD)
                changeCount(item)
            }

            binding.addItem.setOnClickListener {
                item.count += 1
                changeInTheObject(item, CalledFrom.DASHBOARD)
                changeCount(item)
            }

            binding.removeItem.setOnClickListener {
                if (item.count > 0)
                    item.count -= 1
                changeInTheObject(item, CalledFrom.DASHBOARD)
                changeCount(item)
            }
        }

        private fun changeCount(item: WashItemResponseModelItem) {
            if (item.count > 0) {
                binding.itemCount.text = item.count.toString()
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