package com.example.stockmarket.data.mapper

import com.example.stockmarket.data.local.StockListEntity
import com.plcoding.stockmarketapp.domain.model.StockListing

fun StockListEntity.toStockList(): StockListing {
    return StockListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun StockListing.toStockListEntity(): StockListEntity {
    return StockListEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}