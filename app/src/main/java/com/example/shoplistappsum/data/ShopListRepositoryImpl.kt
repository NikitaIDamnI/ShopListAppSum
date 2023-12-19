package com.example.shoplistappsum.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.shoplistappsum.domain.ShopItem
import com.example.shoplistappsum.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.shoplistappsum.domain.ShopListRepository
import kotlin.random.Random

class ShopListRepositoryImpl
    (
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).ShopListDao()
    private val mapper = ShopListMapper()

    override fun addShopList(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun deleteShopList(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override fun editingShopList(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))

    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val dpModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dpModel)
    }

    override fun getShopList() = shopListDao.getShopList().map {
        mapper.mapListDbModelToEntity(it)
    }


}