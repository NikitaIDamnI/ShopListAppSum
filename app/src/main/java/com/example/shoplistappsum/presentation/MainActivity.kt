package com.example.shoplistappsum.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shoplistappsum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var shopListAdapter: ShopListAdapter ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupShopList()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            shopListAdapter?.shopList = it

        }


    }

    private fun setupShopList() {
        val rvShopList = binding.recyclerView
        with(rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter

            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLE,
                ShopListAdapter.MAX_PULL_SIZE
            )

            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLED,
                ShopListAdapter.MAX_PULL_SIZE
            )
        }
       shopListAdapter?.onShopItemLongClickListener ={
           viewModel.changeEnableState(it)

       }

        shopListAdapter?.onShopItemClickListener={
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        }

    }
}