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
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private var _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount


    private var _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseActivity = MutableLiveData<Unit>()
    val shouldCloseActivity: LiveData<Unit>
        get() = _shouldCloseActivity


    fun addShopList(input1: String?, input2: String?) {
        val name = parseName(input1)
        val count = parseCount(input2)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            addShopListUseCase.addShopList(ShopItem(name, count, true))
        }
        finishWork()
    }

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun editingShopItem(input1: String?, input2: String?) {
        val name = parseName(input1)
        val count = parseCount(input2)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let{
                val item = it.copy(name = name, count = count)
                editingShopItemUseCase.editingShopList(item)
                finishWork()
            }
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
            _errorInputName.value = true
            result = false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false

    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false

    }

    private fun finishWork(){
        _shouldCloseActivity.value = Unit
    }


}

