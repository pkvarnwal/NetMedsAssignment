package com.netmedsassignment.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// This is Entity class for room database
@Entity(tableName = "Cart")
data class CartItem(
    @PrimaryKey
    @SerializedName("itemId") val itemId: String,
    @SerializedName("itemName") val itemName: String,
    @SerializedName("minPrice") val minPrice: Int
)