package com.example.shoplistappsum.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoplistappsum.R
import com.example.shoplistappsum.databinding.ItemShopDisabledBinding
import com.example.shoplistappsum.databinding.ItemShopEnabledBinding
import com.example.shoplistappsum.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)?= null
    var onShopItemClickListener: ((ShopItem) -> Unit)?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            ENABLE -> R.layout.item_shop_enabled
            DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = viewHolder.binding

        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener{
            onShopItemClickListener?.invoke(shopItem)
        }

        when(binding){
            is ItemShopEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
            is ItemShopDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enable) {
            ENABLE
        } else {
            DISABLED
        }
    }

    companion object {
        const val ENABLE = 0
        const val DISABLED = 1
        const val MAX_PULL_SIZE = 20
    }


}