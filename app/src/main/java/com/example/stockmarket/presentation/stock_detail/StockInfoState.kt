package com.example.stockmarket.presentation.stock_detail

import com.plcoding.stockmarketapp.domain.model.IntraDayInfo
import com.plcoding.stockmarketapp.domain.model.StockInfo

data class StockInfoState(
    val intraDayInfo: List<IntraDayInfo> = emptyList(),
    val stockInfo: StockInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
