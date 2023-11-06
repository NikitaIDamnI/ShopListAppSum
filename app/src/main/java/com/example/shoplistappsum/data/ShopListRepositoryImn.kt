package com.example.shoplistappsum.data

import com.example.shoplistappsum.domain.ShopItem
import com.example.shoplistappsum.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.shoplistappsum.domain.ShopListRepository

object ShopListRepositoryImn: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var  autoIncrementId = 0


    override fun addShopList(shopItem: ShopItem) {
        if(shopItem.id == UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopList(item: ShopItem) {
        shopList.remove(item)
    }

    override fun editingShopList(item: ShopItem) {
        val oldShopItem = getShopItem(item.id)
        shopList.remove(oldShopItem)
        shopList.add(item)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}