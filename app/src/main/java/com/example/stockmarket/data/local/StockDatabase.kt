package com.example.stockmarket.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StockListEntity::class], version = 1)
abstract class StockDatabase : RoomDatabase() {

    abstract val dao :StockDao
}