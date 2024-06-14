package com.example.stockmarket.presentation.stock_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun StockListingScreen(
    state: StockListingState,
    event: (StockListingEvent) -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
    ) {
        OutlinedTextField(value = state.searchQuery, onValueChange = {
            event(StockListingEvent.SearchQuery(it))
        })
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            event(StockListingEvent.RefreshEvent)
        }) {
            LazyColumn {
                items(state.stocks.size) {
                    StockItem(stockList = state.stocks[it],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                    )
                    if (it < state.stocks.size) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewStockListingScreen() {
    StockListingScreen(StockListingState()) {}
}