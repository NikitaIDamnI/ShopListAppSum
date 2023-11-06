package com.example.shoplistappsum.domain

class DeleteShopItemUseCase(private val shopListRepository:ShopListRepository) {
    fun deleteShopItemList(shopItem: ShopItem){
        shopListRepository.deleteShopList(shopItem)
    }
}