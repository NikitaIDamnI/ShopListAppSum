package com.example.shoplistappsum.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplistappsum.data.ShopListRepositoryImpl
import com.example.shoplistappsum.domain.AddShopListUseCase
import com.example.shoplistappsum.domain.EditingShopItemUseCase
import com.example.shoplistappsum.domain.GetShopItemUseCase
import com.example.shoplistappsum.domain.ShopItem
import java.lang.Exception


class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val addShopListUseCase = AddShopListUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editingShopItemUseCase = EditingShopItemUseCase(repository)

    private var _errorInputName = MutableLiveData<Boolean>()
    val errorInputName :LiveData<Boolean>
        get() = _errorInputName

    fun addShopList(input1: String?, input2: String?) {
        val name = parseName(input1)
        val count = parseCount(input2)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            addShopListUseCase.addShopList(ShopItem(name, count, true))
        }
    }

    fun getShopItem(shopItemId: Int) {
        getShopItemUseCase.getShopItem(shopItemId)
    }

    fun editingShopItem(shopItem:ShopItem,input1: String?, input2: String?) {
        val name = parseName(input1)
        val count = parseCount(input2)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val newShopItem = shopItem.copy(name= name, count= count)
            editingShopItemUseCase.editingShopList(newShopItem)
        }


    }

    private fun parseName(input1: String?): String {

        return input1?.trim() ?: ""
    }

    private fun parseCount(input2: String?): Int {
        return try {
            input2?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            //TODO: show error input name
            result = false
        }

        if (count < 0) {
            //TODO: show error input name
            result = false
        }

        return result
    }

}

