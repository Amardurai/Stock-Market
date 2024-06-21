package com.example.stockmarket.presentation.stock_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.stockmarketapp.domain.model.IntraDayInfo

@Composable
fun StockChart(
    info: List<IntraDayInfo> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {

    val spacing = 100f
}

@Preview
@Composable
private fun PreviewStockChart() {
    StockChart()
}