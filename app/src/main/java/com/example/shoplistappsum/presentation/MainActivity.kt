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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this){
            showList(it)
        }

    }

    private fun showList(list: List<ShopItem>) = with(binding) {

        llShopList.removeAllViews()

        for (shopItem in list){

            val layoutId = if(shopItem.enable){
                R.layout.item_shop_enabled
            }else{
                R.layout.item_shop_disabled
            }

            val view = LayoutInflater.from(root.context).inflate(layoutId,binding.llShopList,false)
            val tvName = view.findViewById<TextView>(R.id.tvName)
            val tvCount = view.findViewById<TextView>(R.id.tvCount)

            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()

            view.setOnClickListener {
                viewModel.changeEnableState(shopItem)
               //true
            }
            binding.llShopList.addView(view)




        }

    }

}