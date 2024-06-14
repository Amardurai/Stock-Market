package com.example.stockmarket.di

import com.example.stockmarket.data.repository.StockRepositoryImp
import com.example.stockmarket.domain.respository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImp: StockRepositoryImp): StockRepository
}