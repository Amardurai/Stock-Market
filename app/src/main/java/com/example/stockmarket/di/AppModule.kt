package com.example.stockmarket.di

import android.app.Application
import androidx.room.Room
import com.example.stockmarket.data.local.StockDatabase
import com.example.stockmarket.data.remote.StockAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val BASE_URL = "https://www.alphavantage.co"
    const val API_KEY = "496G4KNYKE0BRVHL"


    @Provides
    @Singleton
    fun provideStockAPI(): StockAPI {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build()
            .create(StockAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stockdb.db"
        ).build()
    }

}