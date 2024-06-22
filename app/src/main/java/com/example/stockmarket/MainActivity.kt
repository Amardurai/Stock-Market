package com.example.stockmarket

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.stockmarket.presentation.stock_detail.StockInfoScreen
import com.example.stockmarket.presentation.stock_detail.StockInfoViewModel
import com.example.stockmarket.presentation.stock_listing.StockListingScreen
import com.example.stockmarket.presentation.stock_listing.StockListingViewModel
import com.example.stockmarket.ui.theme.StockMarketTheme
import com.example.stockmarket.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockMarketTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Routes.StockListing, // custom type for first screen
                        ) {
                            composable<Routes.StockListing> {
                                val viewmodel: StockListingViewModel = hiltViewModel()
                                StockListingScreen(state = viewmodel.uiState, viewmodel::onEvent,navController)
                            }

                            composable<Routes.StockDetail> {
                                val viewmodel: StockInfoViewModel = hiltViewModel(it)
                                StockInfoScreen(uistate = viewmodel.uiState.value)
                            }

                        }

                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StockMarketTheme {
    }
}