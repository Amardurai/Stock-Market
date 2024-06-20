package com.example.stockmarket.presentation.stock_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarket.domain.respository.StockRepository
import com.example.stockmarket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListingViewModel @Inject constructor(val stockRepository: StockRepository) :
    ViewModel() {

    var uiState by mutableStateOf(StockListingState())
        private set
    private var searchJob: Job? = null


    init {
        getStockListing(true)
    }

    fun onEvent(event: StockListingEvent) {
        when (event) {
            is StockListingEvent.RefreshEvent -> {
                getStockListing(fetchFromRemote = true)
            }

            is StockListingEvent.SearchQuery -> {
                uiState = uiState.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getStockListing()
                }
            }
        }
    }

    private fun getStockListing(
        fetchFromRemote: Boolean = false,
        searchQuery: String = uiState.searchQuery
    ) {
        viewModelScope.launch {
            stockRepository.getStockList(fetchFromRemote, searchQuery)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let {
                                uiState = uiState.copy(stocks = it)
                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            uiState = uiState.copy(isLoading = result.isLoading)
                        }
                    }

                }
        }
    }


}