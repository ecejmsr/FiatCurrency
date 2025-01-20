package com.bps.fiatscape.ui.landing

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bps.fiatscape.common.base.BaseViewModel
import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.network.api.CoinPaprikaRepo
import com.bps.fiatscape.common.util.TimeUtils.refreshDataDate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel  @Inject constructor(
    @ApplicationContext val app: Context,
    private val repo: CoinPaprikaRepo
): BaseViewModel(app) {
    private val _coinList = MutableLiveData<List<Coin>>()
    val coinList: LiveData<List<Coin>> = _coinList

    private val _lastRefreshed = MutableLiveData<String>()
    val lastRefreshed: LiveData<String> = _lastRefreshed

    private val _currUSDPrice = MutableLiveData<Double>()
    val currUSDPrice: LiveData<Double> = _currUSDPrice

    private val _loadingPrice = MutableLiveData<Boolean>()
    val loadingPrice: LiveData<Boolean> = _loadingPrice

    init {
        fetchCoinList()
        fetchTickerData()
    }

    private fun fetchCoinList() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.listCoins().collect { response ->
                when (response) {
                    is APIResponse.Loading -> {
                        _coinList.postValue(listOf())
                        _loading.postValue(true)
                        _error.postValue(null)
                    }

                    is APIResponse.Success -> {
                        _loading.postValue(false)
                        _coinList.postValue(response.data)
                    }

                    is APIResponse.Error -> {
                        _loading.postValue(false)
                        _error.postValue(response.message)
                    }
                }
            }

            refreshDataDate(_lastRefreshed, app)
        }
    }

    fun fetchTickerData() {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                while (true) {
                    emit(repo.getTickersById("btc-bitcoin"))
                    delay(60_000L)
                }
            }.collect { responseFlow ->
                responseFlow.collect { response ->
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

                refreshDataDate(_lastRefreshed, app)
            }
        }
    }

    fun navigateToCoinOverview(coin: Coin) {
        navigationCommand.value =
            LandingFragmentDirections.actionLandingFragmentToCoinOverviewFragment(coin)
    }
}
