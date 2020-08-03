package com.netmedsassignment.data.repositories

import com.netmedsassignment.data.api.RetrofitBuilder

//This is a repository class to call the API
class MainRepository {

    suspend fun getResponse() = RetrofitBuilder.apiService.getResponseDatas()
}