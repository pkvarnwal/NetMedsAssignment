package com.netmedsassignment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.netmedsassignment.app.MyApplication
import com.netmedsassignment.data.api.Resource
import com.netmedsassignment.data.repositories.MainRepository
import com.netmedsassignment.database.entities.ApiItem
import com.netmedsassignment.database.entities.CartItem
import com.netmedsassignment.database.manager.DatabaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    /*
    This method return a live data
    First it return the loading status  after that it fetch the data from database and emit it.
    then it call the api to fetch TestApi and save all those in database.
    After that fetch the data from database and emit it.
     */
    fun getResponse() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val databaseManager = DatabaseManager.getDatabase(MyApplication.mInstance)
            emit(Resource.success(databaseManager.testApiDao().getAllTestApi()))

            val response = MainRepository().getResponse()
            val apiItems = ArrayList<ApiItem>()
            for (item in response) {
                val apiItem = ApiItem(item.itemId, item.itemName, item.minPrice, false)
                apiItems.add(apiItem)
            }

            databaseManager.testApiDao().saveTestApiDatas(apiItems)

            emit(Resource.success(databaseManager.testApiDao().getAllTestApi()))
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }

    // It will fetch the api list on basis of query and emit that result as livedata
    fun searchTestApi(query: String) = liveData {
        emit(DatabaseManager.getDatabase(MyApplication.mInstance).testApiDao().getTestApi(query))
    }

    // when use select the item update that item to database so that when we search from database that item should show as selected
    fun updateApiItem(apiItem: ApiItem){
        GlobalScope.launch(Dispatchers.IO) {
            val databaseManager = DatabaseManager.getDatabase(MyApplication.mInstance)
            databaseManager.testApiDao().updateTestApi(apiItem.isSelected, apiItem.itemId)
        }
    }

    // When item is checked it is stored in Cart table
    fun saveItemInCart(cartItem: CartItem){
        GlobalScope.launch(Dispatchers.IO) {
            val databaseManager = DatabaseManager.getDatabase(MyApplication.mInstance)
            databaseManager.testApiDao().saveItem(cartItem)
        }
    }

    // When item is checked it is remove from Cart table
    fun removeItemFromCart(cartItem: CartItem){
        GlobalScope.launch(Dispatchers.IO) {
            val databaseManager = DatabaseManager.getDatabase(MyApplication.mInstance)
            databaseManager.testApiDao().deleteItem(cartItem)
        }
    }

    // It will give us the total item added in cart. Each item can be add only once.
    fun getCartItemCount() = liveData {
        val databaseManager = DatabaseManager.getDatabase(MyApplication.mInstance)
        emitSource(databaseManager.testApiDao().getAllCartItem())
    }
}