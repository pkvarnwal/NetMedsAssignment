package com.netmedsassignment.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.netmedsassignment.database.entities.ApiItem
import com.netmedsassignment.database.entities.CartItem

//DAO class is use to define all the queries in Room Database
@Dao
interface TestApiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTestApiDatas(testApiDatas: List<ApiItem>)

    @Query("SELECT * FROM TestApiTable")
    fun getAllTestApi(): List<ApiItem>

    @Query("SELECT * FROM TestApiTable  WHERE itemName LIKE :query")
    suspend fun getTestApi(query: String): List<ApiItem>

    @Query("UPDATE TestApiTable SET isSelected = :checked WHERE itemId = :itemId")
    suspend fun updateTestApi(checked: Boolean, itemId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(cartItem: CartItem)

    @Delete
    suspend fun deleteItem(cartItem: CartItem)

    @Query("SELECT * FROM Cart")
    fun getAllCartItem(): LiveData<List<CartItem>>
}

