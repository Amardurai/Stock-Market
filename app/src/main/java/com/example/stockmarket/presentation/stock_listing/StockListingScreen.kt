package com.example.stockmarket.presentation.stock_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stockmarket.utils.Routes
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun StockListingScreen(
    state: StockListingState,
    event: (StockListingEvent) -> Unit,
    navController: NavHostController
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            value = state.searchQuery,
            onValueChange = {
                event(StockListingEvent.SearchQuery(it))
            })
        SwipeRefresh(
            modifier = Modifier.padding(horizontal = 16.dp),
            state = swipeRefreshState,
            onRefresh = {
                event(StockListingEvent.RefreshEvent)
            }) {
            LazyColumn {
                items(state.stocks.size) {
                    StockItem(stockList = state.stocks[it],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Routes.StockDetail(state.stocks[it].symbol))
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
    StockListingScreen(StockListingState(), {}, rememberNavController())
}