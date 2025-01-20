package com.bps.fiatscape.common.network.api

import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.dataclasses.Ohlc
import com.bps.fiatscape.common.dataclasses.Ticker
import kotlinx.coroutines.flow.Flow

interface CoinPaprikaRepo {
    suspend fun listCoins(): Flow<APIResponse<List<Coin>>>

    suspend fun getHistoricOHCL(coinId: String, startDate: String, endDate: String): Flow<APIResponse<List<Ohlc>>>

    suspend fun getTickersById(coinId: String): Flow<APIResponse<Ticker>>
}
