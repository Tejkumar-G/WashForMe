package com.example.washforme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.washforme.databinding.SlotDateItemBinding
import com.example.washforme.viewModel.SlotViewModel
import java.text.SimpleDateFormat
import java.util.*

class SlotDateAdapter(private var dateList: List<Triple<String, String, String>>, private val viewModel: SlotViewModel) :
    RecyclerView.Adapter<SlotDateAdapter.ViewHolder>() {
    private val currentDate = Calendar.getInstance().apply {
        this.set(Calendar.HOUR_OF_DAY, 0)
        this.set(Calendar.MINUTE, 0)
        this.set(Calendar.SECOND, 0)
        this.set(Calendar.MILLISECOND, 0)
    }.time


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(SlotDateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int = dateList.size

    fun updateDateList(dateList: List<Triple<String, String, String>>) {
        if (dateList.isNotEmpty()) {
            this.dateList = dateList
            updateTimeSlots(0)
            notifyItemRangeInserted(0, dateList.size)
        }
    }

    private fun updateTimeSlots(position: Int) {
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        parseToDate(position)?.let {
            viewModel.getPickUpSlotsByDate(outputDateFormat.format(it))
        }
    }

    private fun parseToDate(position: Int):  Date? {
        val inputDateFormat = SimpleDateFormat("yyyy, dd MMM, EEE", Locale.getDefault())
        return inputDateFormat.parse("${dateList[position].third}, ${dateList[position].second}, ${dateList[position].first}" )
    }

    inner class ViewHolder(private val binding: SlotDateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(position: Int) {

            if (parseToDate(position) == currentDate)
                binding.date = Triple(dateList[position].first, "Today", dateList[position].third)
            else
                binding.date = dateList[position]

            changeColors(position)

            binding.totalLayout.setOnClickListener {
                updateTimeSlots(position)

            }
        }

        private fun changeColors(position: Int) {
//            if (position == 0) {
//                binding.totalLayout.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.button_color))
//                binding.txtDay.setTextColor(ContextCompat.getColor(binding.root.context, R.color.primary_white))
//            } else {
//                binding.totalLayout.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.primary_white))
//                binding.txtDay.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
//            }
        }
    }
}