package com.example.shoplistappsum.domain

class EditingShopItemUseCase(private val shopListRepository:ShopListRepository) {
    fun editingShopList(shopItem: ShopItem){
       shopListRepository.editingShopList(shopItem)
    }
}