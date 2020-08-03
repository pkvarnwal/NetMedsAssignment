package com.netmedsassignment.ui.cart

import androidx.lifecycle.*
import com.netmedsassignment.app.MyApplication
import com.netmedsassignment.data.api.Resource
import com.netmedsassignment.data.repositories.MainRepository
import com.netmedsassignment.database.entities.ApiItem
import com.netmedsassignment.database.entities.CartItem
import com.netmedsassignment.database.manager.DatabaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    // This method return the List of Cart Item
    fun getAllCartItems() = liveData(Dispatchers.IO) {
        val databaseManager = DatabaseManager.getDatabase(MyApplication.mInstance)
        emitSource(databaseManager.testApiDao().getAllCartItem())
    }

    // It will give the total amount of Items that are added in the cart
    fun getTotal(): LiveData<Int> {
        val databaseManager = DatabaseManager.getDatabase(MyApplication.mInstance)
        return Transformations.map(databaseManager.testApiDao().getAllCartItem()){data -> data.map { it.minPrice }.sum()}
    }

}