package com.example.shoplistappsum.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplistappsum.data.ShopListRepositoryImpl
import com.example.shoplistappsum.domain.DeleteShopItemUseCase
import com.example.shoplistappsum.domain.EditingShopItemUseCase
import com.example.shoplistappsum.domain.GetShopListUseCase
import com.example.shoplistappsum.domain.ShopItem

import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditingShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItemList(shopItem: ShopItem) {
       viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItemList(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(enable = !shopItem.enable)
        viewModelScope.launch {
            editShopItemUseCase.editingShopList(newShopItem)
        }
    }

}