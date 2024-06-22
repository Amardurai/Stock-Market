package com.example.stockmarket.presentation.stock_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockInfoScreen(
    uistate: StockInfoState,
) {
    if (uistate.error == null) {

        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            uistate.stockInfo?.let { stock ->
                Text(
                    text = stock.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stock.symbol,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomHorizontalDivider()

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Industry: ${stock.industry}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Country: ${stock.country}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomHorizontalDivider()

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stock.description,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (uistate.intraDayInfo.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Market Summary")
                    Spacer(modifier = Modifier.height(16.dp))
                    StockChart(
                        infos = uistate.intraDayInfo,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(250.dp)
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uistate.isLoading) {
            CircularProgressIndicator()
        } else if (uistate.error != null) {
            Text(
                text = uistate.error,
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}

@Composable
private fun CustomHorizontalDivider() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxSize()
            .height(1.dp),
        color = Color.Gray,
        thickness = 1.dp
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewStockInfoScreen() {
    StockInfoScreen(StockInfoState())
}