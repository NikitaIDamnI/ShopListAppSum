package com.example.shoplistappsum.domain

interface ShopListRepository {
    fun addShopList(shopItem: ShopItem)
    fun deleteShopList(item: ShopItem)
    fun editingShopList(item: ShopItem)
    fun getShopItem(shopItemId: Int): ShopItem
    fun getShopList(): List<ShopItem>





    }