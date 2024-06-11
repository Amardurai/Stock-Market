package com.example.stockmarket.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetStockList(stockList: List<StockListEntity>)

    @Query("DELETE from stocklistentity")
    suspend fun clearStockList()

    @Query("""SELECT * FROM StockListEntity WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR UPPER(:query) == symbol """)
    suspend fun searchStock(query: String): List<StockListEntity>

}