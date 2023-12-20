package com.example.shoplistappsum.domain

class AddShopListUseCase(private val shopListRepository:ShopListRepository) {
suspend fun addShopList(shopItem: ShopItem){
        shopListRepository.addShopList(shopItem)
    }
}