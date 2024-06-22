package com.example.stockmarket.presentation.stock_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.stockmarket.domain.respository.StockRepository
import com.example.stockmarket.utils.Resource
import com.example.stockmarket.utils.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockInfoViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val stockRepository: StockRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(StockInfoState())
    val uiState: StateFlow<StockInfoState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val args = savedStateHandle.toRoute<Routes.StockDetail>()

            _uiState.value = _uiState.value.copy(isLoading = true)
            val stockInfo = async { stockRepository.getStockInfo(args.symbol) }
            val intraDayInfo = async { stockRepository.getIntradayInfo(args.symbol) }

            when (val result = stockInfo.await()) {
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }

                is Resource.Success -> _uiState.value = _uiState.value.copy(
                    stockInfo = result.data,
                    isLoading = false
                )

                is Resource.Loading -> Unit
            }


            when (val result = intraDayInfo.await()) {
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }

                is Resource.Success -> _uiState.value = _uiState.value.copy(
                    intraDayInfo = result.data.orEmpty(),
                    isLoading = false
                )

                is Resource.Loading -> Unit
            }

        }
    }

}


