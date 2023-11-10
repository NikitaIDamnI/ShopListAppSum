package com.example.shoplistappsum.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
        shopListAdapter = ShopListAdapter()
        rvShopList. adapter = shopListAdapter

        setupPullAdapter(rvShopList)
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper
        .SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter!!.shopList[viewHolder.adapterPosition]
                viewModel.deleteShopItemList(item)
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }


    private fun setupClickListener() {
        shopListAdapter?.onShopItemClickListener = {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter?.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)

        }
    }

    private fun setupPullAdapter(rvShopList:RecyclerView) {
       
        rvShopList. recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLE,
                ShopListAdapter.MAX_PULL_SIZE
            )

        rvShopList. recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLED,
                ShopListAdapter.MAX_PULL_SIZE
            )
       
    }
}