package com.example.shoplistappsum.data

import android.app.Application

import androidx.lifecycle.MediatorLiveData

import com.example.shoplistappsum.domain.ShopItem
import com.example.shoplistappsum.domain.ShopListRepository

class ShopListRepositoryImpl
    (
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).ShopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopList(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopList(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override suspend fun editingShopList(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))

    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dpModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dpModel)
    }

    override fun getShopList() = MediatorLiveData<List<ShopItem>>().apply {
        addSource(shopListDao.getShopList()){
          value =  mapper.mapListDbModelToEntity(it)
    }
    }



}