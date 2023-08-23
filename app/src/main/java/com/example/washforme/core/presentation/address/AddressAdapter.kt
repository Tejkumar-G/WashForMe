package com.example.washforme.core.presentation.address

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.core.domain.model.UserAddress
import com.example.washforme.databinding.AddressItemBinding
import com.example.washforme.utils.Constants
import com.google.gson.Gson

class AddressAdapter(
    private var userAddresses: List<UserAddress>,
    private val viewModel: AddressViewModel
) : RecyclerView.Adapter<AddressAdapter.ItemViewHolder>() {

    var currentAddressIndex = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            AddressItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int = userAddresses.size

    @SuppressLint("NotifyDataSetChanged")
    fun storeData(userAddresses: List<UserAddress>) {
        if (userAddresses.isEmpty()) {
            return
        }
        this.userAddresses = userAddresses
        currentAddressIndex = userAddresses.indexOfFirst { it.currentAddress == true }
        notifyDataSetChanged()

    }

    inner class ItemViewHolder(private val binding: AddressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(position: Int) {
            binding.userAddress = userAddresses[position]


            binding.isAddressSelected = (position == currentAddressIndex).also {
                if (it) {
                    viewModel.pref.set(
                        Constants.CURRENT_ADDRESS,
                        Gson().toJson(userAddresses[position])
                    )
                    viewModel.addressChanged.postValue(true)
                }
            }

            binding.addressCard.setOnClickListener {
                if (position==currentAddressIndex)
                    return@setOnClickListener
                userAddresses[position].id?.let { it1 -> viewModel.changeCurrentAddress(it1) }
                userAddresses[position].currentAddress = true
                userAddresses[currentAddressIndex].currentAddress = false
                val oldCurrentIndex = currentAddressIndex
                currentAddressIndex = position
//                notifyDataSetChanged()
                notifyItemChanged(oldCurrentIndex)
                notifyItemChanged(currentAddressIndex)
            }

            binding.editAddress.setOnClickListener {
                viewModel.addressForEditingSingleEvent.postValue(userAddresses[position])
            }
        }
    }
}