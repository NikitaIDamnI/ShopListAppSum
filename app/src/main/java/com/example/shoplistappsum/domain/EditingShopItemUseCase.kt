package com.example.shoplistappsum.domain

class EditingShopItemUseCase(private val shopListRepository:ShopListRepository) {
    suspend fun editingShopList(shopItem: ShopItem){
       shopListRepository.editingShopList(shopItem)
    }
}