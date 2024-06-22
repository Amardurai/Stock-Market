package com.example.stockmarket.presentation.stock_listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.stockmarketapp.domain.model.StockListing

@Composable
fun StockItem(
    stockList: StockListing,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row {
            Text(
                maxLines = 1,
                text = stockList.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stockList.exchange,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "(${stockList.symbol})")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StockItemPreview() {
    StockItem(
        stockList = StockListing(
            "NameNameNameNameNameNameNameNameNameNameNameNameName",
            "100",
            "Test"
        )
    )
}