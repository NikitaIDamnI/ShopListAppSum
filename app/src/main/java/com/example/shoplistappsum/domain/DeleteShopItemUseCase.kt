package com.example.shoplistappsum.domain

class DeleteShopItemUseCase(private val shopListRepository:ShopListRepository) {
    fun deleteShopList(shopItem: ShopItem){
        shopListRepository.deleteShopList(shopItem)
    }
}