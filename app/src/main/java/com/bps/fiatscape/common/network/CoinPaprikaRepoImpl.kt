package com.bps.fiatscape.common.network

import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getHistoricOHCL() {
        TODO("Not yet implemented")
    }

    override suspend fun getCoinExchangesByID() {
        TODO("Not yet implemented")
    }
}
