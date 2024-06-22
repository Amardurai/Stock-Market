package com.example.stockmarket.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object StockListing : Routes()

    @Serializable
    data class StockDetail(val symbol: String) : Routes()

}