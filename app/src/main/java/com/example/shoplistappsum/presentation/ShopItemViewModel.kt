package com.example.shoplistappsum.presentation

import androidx.lifecycle.ViewModel
import com.example.shoplistappsum.data.ShopListRepositoryImpl
import com.example.shoplistappsum.domain.AddShopListUseCase
import com.example.shoplistappsum.domain.EditingShopItemUseCase
import com.example.shoplistappsum.domain.GetShopItemUseCase


class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val addShopListUseCase = AddShopListUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editingShopItemUseCase = EditingShopItemUseCase(repository)

    fun addShopList(){

    }

}