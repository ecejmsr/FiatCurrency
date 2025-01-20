package com.bps.fiatscape.common.dataclasses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ticker(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    @Json(name = "circulating_supply")
    val circulatingSupply: Long?,
    @Json(name = "total_supply")
    val totalSupply: Long?,
    @Json(name = "max_supply")
    val maxSupply: Long?,
    @Json(name = "beta_value")
    val betaValue: Double?,
    @Json(name = "first_data_at")
    val firstDataAt: String?,
    @Json(name = "last_updated")
    val lastUpdated: String?,
    val quotes: Quotes?
)

@JsonClass(generateAdapter = true)
data class Quotes(
    @Json(name = "USD")
    val usd: USDQuoteDetail?
)

@JsonClass(generateAdapter = true)
data class USDQuoteDetail(
    val price: Double?,
    @Json(name = "volume_24h")
    val volume24h: Double?,
    @Json(name = "volume_24h_change_24h")
    val volumeChange24h: Double?,
    @Json(name = "market_cap")
    val marketCap: Long?,
    @Json(name = "market_cap_change_24h")
    val marketCapChange24h: Double?,
    @Json(name = "percent_change_15m")
    val percentChange15m: Double?,
    @Json(name = "percent_change_30m")
    val percentChange30m: Double?,
    @Json(name = "percent_change_1h")
    val percentChange1h: Double?,
    @Json(name = "percent_change_6h")
    val percentChange6h: Double?,
    @Json(name = "percent_change_12h")
    val percentChange12h: Double?,
    @Json(name = "percent_change_24h")
    val percentChange24h: Double?,
    @Json(name = "percent_change_7d")
    val percentChange7d: Double?,
    @Json(name = "percent_change_30d")
    val percentChange30d: Double?,
    @Json(name = "percent_change_1y")
    val percentChange1y: Double?,
    @Json(name = "ath_price")
    val athPrice: Double?,
    @Json(name = "ath_date")
    val athDate: String?,
    @Json(name = "percent_from_price_ath")
    val percentFromPriceAth: Double?
)

