package com.example.stockmarket.data.repository

import com.example.stockmarket.data.local.StockDatabase
import com.example.stockmarket.data.mapper.toStockList
import com.example.stockmarket.data.mapper.toStockListEntity
import com.example.stockmarket.data.remote.StockAPI
import com.example.stockmarket.domain.respository.StockRepository
import com.example.stockmarket.utils.Resource
import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.domain.model.StockListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.io.InputStream
import java.io.InputStreamReader

class StockRepositoryImp(
    val stockAPI: StockAPI,
    val database: StockDatabase
) : StockRepository {
    private val dao = database.dao
    override suspend fun getStockList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<StockListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localData = dao.searchStock(query)

            emit(Resource.Success(localData.map { it.toStockList() }))

            val isDbEmpty = localData.isEmpty() && query.isBlank()

            val shouldLoadItFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldLoadItFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            try {
                val response = stockAPI.getStockListing()
                val stockListing = parseCsvFile(response.byteStream())

                dao.clearStockList()
                dao.insetStockList(stockListing.map { it.toStockListEntity() })

                emit(Resource.Success(dao.searchStock("").map { it.toStockList() }))
                emit(Resource.Loading(false))

            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                e.printStackTrace()
            }

        }
    }


    override suspend fun parseCsvFile(byteStream: InputStream): List<StockListing> {
        val reader = CSVReader(InputStreamReader(byteStream))
        return withContext(Dispatchers.IO) {
            reader.readAll().drop(0).mapNotNull {
                val symbol = it.getOrNull(0)
                val name = it.getOrNull(1)
                val exchange = it.getOrNull(2)
                StockListing(
                    name = name ?: return@mapNotNull null,
                    symbol = symbol ?: return@mapNotNull null,
                    exchange = exchange ?: return@mapNotNull null
                )
            }.also {
                reader.close()
            }
        }
    }

}