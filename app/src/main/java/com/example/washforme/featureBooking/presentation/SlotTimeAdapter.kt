package com.example.washforme.featureBooking.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.databinding.SlotTimeItemBinding
import com.example.washforme.featureBooking.domain.models.Timeslot

class SlotTimeAdapter : RecyclerView.Adapter<SlotTimeAdapter.ViewHolder>() {

    private var slots: List<Timeslot> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun addSlots(slots: List<Timeslot>) {
        this.slots = slots
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(SlotTimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(slots[position])
    }

    override fun getItemCount(): Int = slots.size

    class ViewHolder(val binding: SlotTimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(slot: Timeslot) {
            binding.timeSlot = slot
        }
    }
}