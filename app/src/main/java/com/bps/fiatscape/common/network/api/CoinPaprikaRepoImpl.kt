package com.bps.fiatscape.common.network.api

import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.dataclasses.Ohlc
import com.bps.fiatscape.common.dataclasses.Ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class CoinPaprikaRepoImpl(private val api: CoinPaprikaAPI): CoinPaprikaRepo {

    override suspend fun listCoins(): Flow<APIResponse<List<Coin>>> = flow {
        emit(APIResponse.Loading)
        try {
            val response = api.listCoins()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && response.code() == 200) {
                    Timber.d("listCoins call successful :: ${response.code()}")
                    emit(APIResponse.Success(body))
                } else {
                    Timber.e("listCoins failed with code: ${response.code()} and body was $body")
                    emit(APIResponse.Error(response.code(), response.errorBody().toString()))
                }
            } else {
                Timber.e("listCoins failed with code: ${response.code()} :: ${response.raw()}")
                emit(APIResponse.Error(response.code(), response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Timber.e("listCoins failed exception: ${e.localizedMessage}")
            emit(APIResponse.Error(500, e.localizedMessage ?: "FATAL ERROR"))
        }
    }

    override suspend fun getHistoricOHCL(coinId: String, startDate: String, endDate: String): Flow<APIResponse<List<Ohlc>>> = flow {
        emit(APIResponse.Loading)
        try {
            val response = api.getHistoricalData(coinId, startDate, endDate)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && response.code() == 200) {
                    Timber.d("getHistoricOHCL call successful :: ${response.code()}")
                    emit(APIResponse.Success(body))
                } else {
                    Timber.e("getHistoricOHCL failed with code: ${response.code()} and body was $body")
                    emit(APIResponse.Error(response.code(), response.errorBody().toString()))
                }
            } else {
                Timber.e("getHistoricOHCL failed with code: ${response.code()} :: ${response.raw()}")
                emit(APIResponse.Error(response.code(), response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Timber.e("getHistoricOHCL failed exception: ${e.localizedMessage}")
            emit(APIResponse.Error(500, e.localizedMessage ?: "FATAL ERROR"))
        }
    }

    override suspend fun getTickersById(coinId: String): Flow<APIResponse<Ticker>> = flow {
        emit(APIResponse.Loading)
        try {
            val response = api.getTickerData(coinId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && response.code() == 200) {
                    Timber.d("getTickersById call successful :: ${response.raw()}")
                    emit(APIResponse.Success(body))
                } else {
                    Timber.e("getTickersById failed with code: ${response.code()} and body was $body")
                    emit(APIResponse.Error(response.code(), response.errorBody().toString()))
                }
            } else {
                Timber.e("getTickersById failed with code: ${response.code()} :: ${response.raw()}")
                emit(APIResponse.Error(response.code(), response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Timber.e("getTickersById failed exception: ${e.localizedMessage}")
            emit(APIResponse.Error(500, e.localizedMessage ?: "FATAL ERROR"))
        }
    }.catch { e ->
        Timber.e("getTickersById exception: ${e.localizedMessage}")
        emit(APIResponse.Error(500, e.localizedMessage ?: "FATAL ERROR"))
    }
}
