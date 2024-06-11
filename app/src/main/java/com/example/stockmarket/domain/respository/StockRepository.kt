package com.example.stockmarket.domain.respository

import com.example.stockmarket.utils.Resource
import com.plcoding.stockmarketapp.domain.model.StockListing
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface StockRepository {
    suspend fun getStockList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<StockListing>>>

    suspend fun parseCsvFile(byteStream: InputStream): Any
}