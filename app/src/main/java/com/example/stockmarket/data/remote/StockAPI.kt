package com.example.stockmarket.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getStockListing(
        @Query("apikey") apiKey: String
    ): ResponseBody


}