package com.example.stockmarket.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.stockmarket.data.local.StockDatabase
import com.example.stockmarket.data.mapper.toIntradayInfo
import com.example.stockmarket.data.mapper.toStockInfo
import com.example.stockmarket.data.mapper.toStockList
import com.example.stockmarket.data.mapper.toStockListEntity
import com.example.stockmarket.data.remote.StockAPI
import com.example.stockmarket.domain.respository.StockRepository
import com.example.stockmarket.utils.Resource
import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.plcoding.stockmarketapp.domain.model.IntraDayInfo
import com.plcoding.stockmarketapp.domain.model.StockInfo
import com.plcoding.stockmarketapp.domain.model.StockListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Singleton
class StockRepositoryImp @Inject constructor(
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
                val stockListing = parseCsvFileForStockListing(response.byteStream())

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

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val response = stockAPI.getIntradayInfo(symbol)
            val intraDayInfo = parseCsvFileForIntraDayInfo(response.byteStream())
            Resource.Success(intraDayInfo)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.message)
        } catch (e: HttpException) {
            Resource.Error(e.message)
        }
    }

    override suspend fun getStockInfo(symbol: String): Resource<StockInfo> {
        return try {
            val result = stockAPI.getStockInfo(symbol)
            Resource.Success(result.toStockInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.message)
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }


    override suspend fun parseCsvFileForStockListing(byteStream: InputStream): List<StockListing> {
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

    override suspend fun parseCsvFileForIntraDayInfo(byteStream: InputStream): List<IntraDayInfo> {
        val reader = CSVReader(InputStreamReader(byteStream))
        return withContext(Dispatchers.IO) {
            reader.readAll().drop(0).mapNotNull {
                val date = it.getOrNull(0) ?: return@mapNotNull null
                val close = it.getOrNull(4) ?: return@mapNotNull null
                val intraDayDto = IntradayInfoDto(date, close.toDouble())
                intraDayDto.toIntradayInfo()
            }
                .filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    reader.close()
                }
        }
    }

}