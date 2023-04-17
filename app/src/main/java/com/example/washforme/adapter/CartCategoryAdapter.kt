package com.example.washforme.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.databinding.CartCategoryDetailsBinding
import com.example.washforme.interfaces.ItemToCategory
import com.example.washforme.model.WashCategoryRelation
import com.example.washforme.model.WashItem
import com.example.washforme.viewModel.MainViewModel


class CartCategoryAdapter(
    var cartData: ArrayList<WashCategoryRelation>,
    var viewModel: MainViewModel
) : RecyclerView.Adapter<CartCategoryAdapter.ViewHolder>(), ItemToCategory {
    val listOfShownVariables = arrayListOf<Boolean>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CartCategoryDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
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
        fun setData(position: Int) {
            binding.category = cartData[position].category
            val cartItems = cartData[position].items
            binding.categoryCount.text = "${getItemsCount(cartItems)} Items"
            if (listOfShownVariables[position]) {
                binding.itemsRecyclerView.apply {
                    this.visibility = View.VISIBLE
                    this.alpha = 0f

                    this.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null)
                }
                val cartItemAdapter = CartItemAdapter(arrayListOf(), viewModel, cartData[position].category?.id?:-1, this@CartCategoryAdapter)
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
                listOfShownVariables[position] = !listOfShownVariables[position]
                notifyItemChanged(position)
            }

        }

        private fun getItemsCount(cartItems: ArrayList<WashItem>): String {
            var count = 0
            cartItems.forEach {
                count += it.count
            }
            return count.toString()
        }
    }

    override fun itemsChanged(categoryId: Int, allItemsRemoved: Boolean) {
        var removedPosition = -1
        for (i in 0 until cartData.size){
            if (cartData[i].category?.id == categoryId) {
                removedPosition = i
                break
            }
        }
        if (allItemsRemoved) {
            if (removedPosition!=-1) {
                cartData.removeAt(removedPosition)
                listOfShownVariables.removeAt(removedPosition)
                notifyDataSetChanged()
//            notifyItemRemoved(removedPosition)
            }
        } else {
            notifyItemChanged(removedPosition)
        }

    }
}