package com.bps.fiatscape.common.network

import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import kotlinx.coroutines.flow.Flow

interface CoinPaprikaRepo {
    suspend fun listCoins(): Flow<APIResponse<List<Coin>>>

    suspend fun getHistoricOHCL()

    suspend fun getCoinExchangesByID()
}
