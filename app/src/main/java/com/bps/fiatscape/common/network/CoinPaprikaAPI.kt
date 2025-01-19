package com.bps.fiatscape.common.network

import com.bps.fiatscape.common.dataclasses.Coin
import retrofit2.Response
import retrofit2.http.GET

interface CoinPaprikaAPI {
    @GET("v1/coins")
    suspend fun listCoins(): Response<List<Coin>>
}
