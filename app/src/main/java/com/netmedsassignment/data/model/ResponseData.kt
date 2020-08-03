package com.netmedsassignment.data.model

import com.google.gson.annotations.SerializedName

//This is response of TestAPI
data class ResponseData(
    @SerializedName("itemId") val itemId: String,
    @SerializedName("S.no") val sNo: Int,
    @SerializedName("itemName") val itemName: String,
    @SerializedName("type") val type: String,
    @SerializedName("Keyword") val keyword: String,
    @SerializedName("testCount") val testCount: Int,
    @SerializedName("minPrice") val minPrice: Int,
    @SerializedName("labName") val labName: String,
    @SerializedName("category") val category: String
) {
}