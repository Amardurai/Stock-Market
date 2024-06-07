package com.plcoding.stockmarketapp.domain.model

data class StockListing(
    val name: String,
    val symbol: String,
    val exchange: String,
)
