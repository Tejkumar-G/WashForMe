package com.example.washforme.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.washforme.R
import com.example.washforme.model.Categories

class CategoryAdapter(private var data: ArrayList<Categories>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = R.layout.category_item

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myData = data[position]
        holder.textView.text = myData.name
//        holder.itemView.setBackgroundResource(myData.Image.toInt())
    }

    override fun getItemCount(): Int = data.size

    fun addData(categories: Array<Categories>?) {
        if (categories != null) {
            data.addAll(categories)
            this.notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.header)
    }
}