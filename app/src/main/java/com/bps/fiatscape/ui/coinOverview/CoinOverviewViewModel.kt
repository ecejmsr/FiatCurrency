package com.bps.fiatscape.ui.coinOverview

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bps.fiatscape.R
import com.bps.fiatscape.common.base.BaseViewModel
import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.network.api.CoinPaprikaRepo
import com.bps.fiatscape.common.network.favorites.FavoritesRepo
import com.bps.fiatscape.common.util.TimeUtils.refreshDataDate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoinOverviewViewModel @Inject constructor(
    @ApplicationContext val app: Context,
    private val repo: CoinPaprikaRepo
) : BaseViewModel(app) {

    var coin: Coin? = null

    private val _chartData = MutableLiveData<List<Float>>()
    val chartData: LiveData<List<Float>> = _chartData

    private val _currUSDPrice = MutableLiveData<Double>()
    val currUSDPrice: LiveData<Double> = _currUSDPrice

    private val _lastRefreshed = MutableLiveData<String>()
    val lastRefreshed: LiveData<String> = _lastRefreshed

    private val _loadingPrice = MutableLiveData<Boolean>()
    val loadingPrice: LiveData<Boolean> = _loadingPrice

    fun fetchTickerData() {
        _loadingPrice.value = true
        viewModelScope.launch(Dispatchers.IO) {
            coin?.id?.let {
                repo.getTickersById(it)
                    .retryWhen { _, _ ->
                        delay(60_000L)
                        _loading.postValue(true)
                        true
                    }
                    .collect { response ->
                        when (response) {
                            is APIResponse.Loading -> {
                                _loadingPrice.postValue(true)
                            }

                            is APIResponse.Success -> {
                                _loadingPrice.postValue(false)
                                _currUSDPrice.postValue(response.data.quotes?.usd?.price ?: 0.0)
                            }

                            is APIResponse.Error -> {
                                _loadingPrice.postValue(false)
                                _error.postValue(response.message)
                            }
                        }
                    }
            } ?: Timber.e("Error when attempting to get Ticker data. Coin id not found!")
            refreshDataDate(_lastRefreshed, app)
        }
    }

    // Fetch OHLCV data and transform it for the chart
    fun fetchChartData(coinId: String, startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getHistoricOHCL(coinId, startDate, endDate)
                .collect { response ->
                    when (response) {
                        is APIResponse.Loading -> {
                            _loading.postValue(true)
                        }

                        is APIResponse.Success -> {
                            _loading.postValue(false)
                            val closePrices = response.data.map { it.close.toFloat() }
                            _chartData.postValue(closePrices)
                        }

                        is APIResponse.Error -> {
                            _loading.postValue(false)
                            _error.postValue(response.message)
                        }
                    }
                }
        }
    }
}
