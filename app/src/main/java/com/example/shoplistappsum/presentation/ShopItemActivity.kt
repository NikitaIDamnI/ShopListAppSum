package com.example.shoplistappsum.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.shoplistappsum.databinding.ActivityShopItemBinding
import com.example.shoplistappsum.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding
    private lateinit var shopItemViewModel: ShopItemViewModel

    private var screenMod = UNKNOWN_SCREEN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parsIntent()
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        when (screenMod) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }


    private fun launchEditMode() {
        shopItemViewModel.getShopItem(shopItemId)

        shopItemViewModel.shopItem.observe(this) {
            binding.edName.setText(it.name)
            binding.edCount.setText(it.count.toString())
        }

        binding.button.setOnClickListener {
            if(error()) {
                val name = binding.edName.text.toString()
                val count = binding.edCount.text.toString()
                shopItemViewModel.editingShopItem(name, count)
                finish()
            }
        }
    }

    private fun launchAddMode() {
        binding.button.setOnClickListener {
            if(error()) {
                val name = binding.edName.text.toString()
                val count = binding.edCount.text.toString()
                shopItemViewModel.addShopList(name, count)
                finish()
            }

        }

    }

    private fun error(): Boolean {
        var nameBool = false
        var countBool = false

        if(binding.edName.text.toString().isEmpty()){
            binding.tilName.error="Entity"
        }else{
            nameBool = true
        }

        if (binding.edCount.text.toString().isEmpty()){
            binding.tilCount.error="Entity"
        }else{
            countBool = true
        }

        return if(nameBool && countBool){
            shopItemViewModel.resetErrorInputName()
            shopItemViewModel.resetErrorInputCount()
            true
        }else{
            false
        }


    }


    private fun parsIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is abcent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMod = mode
        if (mode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }

    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val UNKNOWN_SCREEN_MODE = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent

        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent

        }
    }
}