package com.example.shoplistappsum.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplistappsum.databinding.FragmentShopItemBinding
import com.example.shoplistappsum.domain.ShopItem

class ShopItemFragment(
    private var screenMod: String = UNKNOWN_SCREEN_MODE,
    private var shopItemId: Int = ShopItem.UNDEFINED_ID,
    ) : Fragment() {

    private lateinit var binding: FragmentShopItemBinding
    private lateinit var shopItemViewModel: ShopItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseParams()
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        addTextChangeListeners()
        launcher()
        observeViewModel()

    }

    private fun observeViewModel() {
        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Entity"
            } else {
                null
            }
            binding.tilCount.error = message
        }
        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Entity"
            } else {
                null
            }
            binding.tilName.error = message
        }

        shopItemViewModel.shouldCloseActivity.observe(viewLifecycleOwner) {
            activity?.onBackPressed()

        }
    }

    private fun launcher() {
        when (screenMod) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        binding.edName.doOnTextChanged { text, start, before, count -> shopItemViewModel.resetErrorInputName() }
        binding.edCount.doOnTextChanged { text, start, before, count -> shopItemViewModel.resetErrorInputCount() }

    }


    private fun launchEditMode() {
        shopItemViewModel.getShopItem(shopItemId)

        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
            binding.edName.setText(it.name)
            binding.edCount.setText(it.count.toString())
        }

        binding.button.setOnClickListener {

            val name = binding.edName.text.toString()
            val count = binding.edCount.text.toString()
            shopItemViewModel.editingShopItem(name, count)

        }
    }

    private fun launchAddMode() {
        binding.button.setOnClickListener {

            val name = binding.edName.text.toString()
            val count = binding.edCount.text.toString()
            shopItemViewModel.addShopList(name, count)


        }

    }


    private fun parseParams() {
        if (screenMod != MODE_EDIT && screenMod != MODE_ADD) {
            throw RuntimeException("Param screen mode is absent")
        }
        if (screenMod == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
            throw RuntimeException("Param shop item id is absent")

        }
    }




        companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val UNKNOWN_SCREEN_MODE = ""

        fun newInstanceAddItem():ShopItemFragment {

            return ShopItemFragment(MODE_ADD)
        }
        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment{

            return ShopItemFragment(MODE_EDIT, shopItemId)
        }

/*
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

 */
    }
}