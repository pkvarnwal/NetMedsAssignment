package com.netmedsassignment.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.netmedsassignment.R
import com.netmedsassignment.database.entities.CartItem
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_main.recycle

class CartActivity : AppCompatActivity() {

    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setupViewModel()
    }

    //This method is used to create the instance of view model and observe its instance
    @SuppressLint("SetTextI18n")
    private fun setupViewModel() {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartViewModel.getAllCartItems().observe(this, Observer {
           setUpAdapter(it)
        })

        cartViewModel.getTotal().observe(this, Observer {
           text_total_value.text = "Total value: $it"
        })
    }

    //Set up adapter to recycleview
    private fun setUpAdapter(datas: List<CartItem>) {
        val cartAdapter = CartAdapter(this, datas)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = cartAdapter
    }
}