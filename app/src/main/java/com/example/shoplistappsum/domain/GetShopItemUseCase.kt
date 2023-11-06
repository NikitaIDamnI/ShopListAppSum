package com.example.shoplistappsum.domain

class GetShopItemUseCase(private val shopListRepository:ShopListRepository) {

    fun getShopItem(shopItemId: ShopItem): ShopItem{
        return shopListRepository.getShopItem(shopItemId)
    }

}