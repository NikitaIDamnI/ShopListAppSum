package com.example.shoplistappsum.presentation

import androidx.lifecycle.ViewModel
import com.example.shoplistappsum.data.ShopListRepositoryImpl
import com.example.shoplistappsum.domain.DeleteShopItemUseCase
import com.example.shoplistappsum.domain.EditingShopItemUseCase
import com.example.shoplistappsum.domain.GetShopListUseCase
import com.example.shoplistappsum.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

     private val getShopListUseCase = GetShopListUseCase(repository)
     private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
     private val editShopItemUseCase = EditingShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()


    fun deleteShopItemList(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItemList(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem){
        val newShopItem = shopItem.copy(enable = !shopItem.enable)
        editShopItemUseCase.editingShopList(newShopItem)
    }








}