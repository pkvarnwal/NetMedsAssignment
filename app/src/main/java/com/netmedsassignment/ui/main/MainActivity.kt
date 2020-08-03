package com.netmedsassignment.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.netmedsassignment.R
import com.netmedsassignment.data.api.Status.*
import com.netmedsassignment.database.entities.ApiItem
import com.netmedsassignment.database.entities.CartItem
import com.netmedsassignment.ui.cart.CartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ApiAdapter.OnItemSaveListener {

    private lateinit var textCartItemCount: TextView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var apiAdaper: ApiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }


    /* This method is used to observer the data emitting from getResponse()
     and show the view on the basis of the status
     */
    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getResponse().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    LOADING -> {
                        progress.visibility = View.VISIBLE
                    }
                    SUCCESS -> {
                        progress.visibility = View.GONE
                        setUpAdapter(ArrayList(resource.data!!))
                    }

                    ERROR -> {
                        progress.visibility = View.GONE
                    }
                }
            }
        })
    }

    // setting adapter to the recycle view
    private fun setUpAdapter(datas: ArrayList<ApiItem>) {
        apiAdaper = ApiAdapter(this, datas, this)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = apiAdaper
    }

    // this is default android method used to inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        /*
        Fetching the search view from menu and adding the querylistener to implement search
         */
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(onQueryTextListener)


        val menuItem = menu.findItem(R.id.action_cart)
        val actionView = menuItem.actionView
        textCartItemCount = actionView.findViewById<TextView>(R.id.unread_notification_badge)

        // Observing the cart item count, when we sselct an item it is added in cart and when wedeselet it is remove from cart
        mainViewModel.getCartItemCount().observe(this, Observer {
            if (this::textCartItemCount.isInitialized) textCartItemCount.text = it.size.toString()
        })

        actionView.setOnClickListener { v ->
            val notificationIntent = Intent(this, CartActivity::class.java)
            startActivity(notificationIntent)
        }

        return super.onCreateOptionsMenu(menu)
    }


    private var onQueryTextListener: SearchView.OnQueryTextListener = object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getSearchTestApi(newText)
                return true
            }
        }

    // Used to observe the search data on the basis of query
    private fun getSearchTestApi(query: String){
        val searchTextQuery = "%$query%"
        mainViewModel.searchTestApi(searchTextQuery).observe(this, Observer {
            apiAdaper.updateData(it)
        })
    }

    // this is overie method, it is called when check box is clicked, when it is cheked the iteem is ad
    override fun onItemChecked(isChecked: Boolean, data: ApiItem) {
       mainViewModel.updateApiItem(data)
       val cartItem = CartItem(data.itemId, data.itemName, data.minPrice)
       if (isChecked) {
           mainViewModel.saveItemInCart(cartItem)
       } else {
          mainViewModel.removeItemFromCart(cartItem)
       }
    }

}
