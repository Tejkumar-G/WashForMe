package com.example.washforme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.databinding.SlotTimeItemBinding
import com.example.washforme.model.PickUpSlots
import com.example.washforme.viewModel.SlotViewModel

class SlotTimeAdapter(private var slots: List<PickUpSlots>, val viewModel: SlotViewModel) :
    RecyclerView.Adapter<SlotTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(SlotTimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int = slots.size
    fun addSlots(slots: List<PickUpSlots>?) {
        if (slots.isNullOrEmpty())
            return
        this.slots = slots
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: SlotTimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(position: Int) {
            binding.timeSlot = slots[position]
        }
    }
}