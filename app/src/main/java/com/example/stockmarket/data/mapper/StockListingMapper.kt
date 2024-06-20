package com.example.stockmarket.data.mapper

import com.example.stockmarket.data.local.StockListEntity
import com.example.stockmarket.data.remote.dto.StockInfoDto
import com.plcoding.stockmarketapp.domain.model.StockInfo
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


fun StockInfoDto.toStockInfo(): StockInfo {
    return StockInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: "",
    )
}
