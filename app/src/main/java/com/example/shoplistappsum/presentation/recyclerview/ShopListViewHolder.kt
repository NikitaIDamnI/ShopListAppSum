package com.example.shoplistappsum.presentation.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplistappsum.R

class ShopListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    val tvName: TextView = view.findViewById<TextView>(R.id.tvName)
    val tvCount: TextView = view.findViewById<TextView>(R.id.tvCount)
}