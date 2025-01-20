package com.bps.fiatscape.common.network.api

import SearchResponse
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.dataclasses.Ohlc
import com.bps.fiatscape.common.dataclasses.Ticker
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinPaprikaAPI {
    @GET("v1/coins")
    suspend fun listCoins(): Response<List<Coin>>

    @GET("v1/coins/{coinId}/ohlcv/historical")
    suspend fun getHistoricalData(
        @Path("coinId") coinId: String,
        @Query("start") startDate: String,
        @Query("end") endDate: String
    ): Response<List<Ohlc>>

    @GET("v1/tickers/{coinId}")
    suspend fun getTickerData(
        @Path("coinId") coinId: String
    ): Response<Ticker>

    @GET("v1/search")
    suspend fun searchCoin(@Query("q") query: String): Response<SearchResponse>
}
