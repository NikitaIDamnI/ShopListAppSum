package com.example.shoplistappsum.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplistappsum.domain.ShopItem
import com.example.shoplistappsum.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.shoplistappsum.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = sortedSetOf<ShopItem>({ shopItem, shopItem1 -> shopItem.id.compareTo(shopItem1.id) })
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private var  autoIncrementId = 0

    init {
        for (i in 0..1){
            val shopItem = ShopItem("Name $i",i,Random.nextBoolean())
            addShopList(shopItem)
        }
    }


    override fun addShopList(shopItem: ShopItem) {
        if(shopItem.id == UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopList(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    override fun editingShopList(item: ShopItem) {
        val oldShopItem = getShopItem(item.id)
        shopList.remove(oldShopItem)
        addShopList(item)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateList(){
        shopListLD.value = shopList.toList()
    }
}