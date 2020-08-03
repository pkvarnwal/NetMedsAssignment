package com.netmedsassignment.data.api

import com.netmedsassignment.data.model.ResponseData
import retrofit2.http.GET


//This is interface according to retrofit wher we can define all the endPoints
interface ApiService {

    @GET("getTestList")
    suspend fun getResponseDatas(): List<ResponseData>

}