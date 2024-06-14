package com.example.stockmarket.presentation.stock_listing

sealed class StockListingEvent {
    data object RefreshEvent : StockListingEvent()
    data class SearchQuery(val query: String) : StockListingEvent()
}