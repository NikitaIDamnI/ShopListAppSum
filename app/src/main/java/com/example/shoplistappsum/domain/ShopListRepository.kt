package com.example.shoplistappsum.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend  fun addShopList(shopItem: ShopItem)
    suspend fun deleteShopList(item: ShopItem)
    suspend fun editingShopList(item: ShopItem)
    suspend fun getShopItem(shopItemId: Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>





    }