package com.example.stockmarket.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.plcoding.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.plcoding.stockmarketapp.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo(): IntraDayInfo {

    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formater = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formater)
    return IntraDayInfo(
        date = localDateTime,
        close = close
    )
}