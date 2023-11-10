package com.example.shoplistappsum.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shoplistappsum.R
import com.example.shoplistappsum.databinding.ActivityMainBinding
import com.example.shoplistappsum.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupShopList()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            adapter.shopList = it

        }


    }

    private fun setupShopList() {
        val rvShopList = binding.recyclerView

            adapter = ShopListAdapter()
            rvShopList.adapter = adapter

            rvShopList.recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLE,
                ShopListAdapter.MAX_PULL_SIZE
            )

            rvShopList.recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLED,
                ShopListAdapter.MAX_PULL_SIZE
            )



    }
}