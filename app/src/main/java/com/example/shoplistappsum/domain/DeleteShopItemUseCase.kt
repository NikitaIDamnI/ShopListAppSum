package com.example.shoplistappsum.domain

class DeleteShopItemUseCase(private val shopListRepository:ShopListRepository) {
    suspend fun deleteShopItemList(shopItem: ShopItem){
        shopListRepository.deleteShopList(shopItem)
    }
}