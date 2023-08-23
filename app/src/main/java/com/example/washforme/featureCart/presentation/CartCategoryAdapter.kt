package com.example.washforme.featureCart.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.core.domain.model.WashCategoryRelation
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.core.domain.model.WashItemResponseModelItem
import com.example.washforme.databinding.CartCategoryDetailsBinding
import com.example.washforme.featureCart.domain.interfaces.ItemToCategory
import com.example.washforme.utils.CalledFrom


class CartCategoryAdapter : RecyclerView.Adapter<CartCategoryAdapter.ViewHolder>(), ItemToCategory {
    val listOfShownVariables = arrayListOf<Boolean>()

    private var cartData: List<WashCategoryRelation> = emptyList()

    private var changeInTheObject: ((WashCategoryResponseModelItem?, WashItemResponseModelItem, CalledFrom) -> Unit)? = null

    fun setChangeInObject(changeInTheObject: (WashCategoryResponseModelItem?, WashItemResponseModelItem, CalledFrom) -> Unit) {
        this.changeInTheObject = changeInTheObject
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCartData(cartData: List<WashCategoryRelation>) {
        this.cartData = cartData
        listOfShownVariables.addAll(List(cartData.size) { false })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CartCategoryDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(cartData[position])
    }

    override fun getItemCount(): Int = cartData.size
    fun updateCart(cartData: ArrayList<WashCategoryRelation>?) {
        if (cartData != null) {
            this.cartData = cartData
            listOfShownVariables.addAll(List(cartData.size) { false })
            notifyItemRangeInserted(0, cartData.size)
        }
    }

    inner class ViewHolder(private val binding: CartCategoryDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var isViewExpand = false

//        private fun getItemsCount(cartItems: ArrayList<WashItemResponseModelItem>): String {
//            var count = 0
//            cartItems.forEach {
//                count += it.count
//            }
//            return count.toString()
//        }

        @SuppressLint("SetTextI18n")
        fun setData(cartItem: WashCategoryRelation) {
            binding.category = cartItem.category
            val cartItems = cartData[absoluteAdapterPosition].items
            binding.categoryCount.text = "${cartItems.sumOf { it.count }} Items"

            if (isViewExpand) {
                binding.itemsRecyclerView.apply {
                    this.visibility = View.VISIBLE
                    this.alpha = 0f
                    this.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null)
                }

                val cartItemAdapter = CartItemAdapter(
                    arrayListOf(),
                    cartData,
                    cartItem.category?.id ?: "",
                    this@CartCategoryAdapter
                )

                cartItemAdapter.setChangeInObject { item, callFrom ->
                    changeInTheObject?.let { it(cartItem.category, item, callFrom) }
                }

                binding.itemsRecyclerView.adapter = cartItemAdapter
                cartItemAdapter.updateItems(cartItems)
                binding.showListOfItems = true
            } else {
                binding.itemsRecyclerView.apply {
                    this.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                this@apply.visibility = View.GONE
                            }
                        })
                }

                binding.showListOfItems = false

            }

            binding.topLayout.setOnClickListener {
                isViewExpand = !isViewExpand
                notifyItemChanged(absoluteAdapterPosition)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun itemsChanged(categoryId: String, allItemsRemoved: Boolean) {
        var removedPosition = -1
        for (index in cartData.indices) {
            if (cartData[index].category?.id == categoryId) {
                removedPosition = index
                break
            }
        }
        if (allItemsRemoved) {
            if (removedPosition != -1) {
//                removeItem(removedPosition)
                listOfShownVariables.removeAt(removedPosition)
                notifyDataSetChanged()
                notifyItemRemoved(removedPosition)
            }
        } else {
            notifyItemChanged(removedPosition)
        }
    }
}