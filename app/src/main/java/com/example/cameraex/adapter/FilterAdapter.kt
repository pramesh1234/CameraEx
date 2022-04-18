package com.example.cameraex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraex.R

class FilterAdapter(val context: Context, val list: ArrayList<String>) :
    RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    var onItemClick:((Int) -> Unit)?=null
    inner class FilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var filterTV: TextView = itemView.findViewById(R.id.filterTV)
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            LayoutInflater.from(context).inflate(R.layout.filter_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        list.let {
            holder.filterTV.text = it[position]
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(filterList:ArrayList<String>) {
        list.clear()
        list.addAll(filterList)
        notifyDataSetChanged()
    }
    }