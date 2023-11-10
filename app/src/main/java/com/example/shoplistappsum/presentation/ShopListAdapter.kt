package com.example.shoplistappsum.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplistappsum.R
import com.example.shoplistappsum.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var count = 0
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ShopListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvCount = view.findViewById<TextView>(R.id.tvCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder, count: ${count++}")
        val layout = when(viewType){
            ENABLE -> R.layout.item_shop_enabled
            DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopListViewHolder, position: Int) {


        val shopItem = shopList[position]
       val type =  if(shopItem.enable)"Active" else {"No Active"}

        viewHolder.tvName.text = "${shopItem.name} $type"
        viewHolder.tvCount.text = shopItem.count.toString()

        viewHolder.view.setOnClickListener {
            true
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enable){
            ENABLE
        }else{
            DISABLED
        }
    }

    override fun getItemCount(): Int {
       return shopList.size
    }

    companion object{
        const val ENABLE = 0
        const val DISABLED = 1
        const val MAX_PULL_SIZE = 20
    }


}