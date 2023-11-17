package com.example.shoplistappsum.presentation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplistappsum.databinding.FragmentShopItemBinding
import com.example.shoplistappsum.domain.ShopItem

class ShopItemFragment : Fragment() {

    private lateinit var binding: FragmentShopItemBinding
    private lateinit var shopItemViewModel: ShopItemViewModel

    private var screenMod: String = UNKNOWN_SCREEN_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Mylog","onCreate")
        if(savedInstanceState == null)  parseParams()
    }

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
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is abcent")
        }

        val mode = args.getString(SCREEN_MODE)

            if (mode != (MODE_ADD) && mode != (MODE_EDIT)) {
                throw RuntimeException("Unknown screen mode $mode")
            }
            screenMod = mode

        if (mode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID,ShopItem.UNDEFINED_ID)
        }

    }


    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val UNKNOWN_SCREEN_MODE = ""

        fun newInstanceAddItem(): ShopItemFragment {

            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }


        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}