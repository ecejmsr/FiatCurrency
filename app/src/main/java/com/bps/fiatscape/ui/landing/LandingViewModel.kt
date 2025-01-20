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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel  @Inject constructor(
    @ApplicationContext val app: Context,
    private val coinRepo: CoinPaprikaRepo
): BaseViewModel(app) {
    private val _coinList = MutableLiveData<List<Coin>>()
    val coinList: LiveData<List<Coin>> = _coinList

    private val _lastRefreshed = MutableLiveData<String>()
    val lastRefreshed: LiveData<String> = _lastRefreshed

    init {
        fetchCoinList()
    }

    fun fetchCoinList() {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepo.listCoins().collect { response ->
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

    fun navigateToCoinOverview(coin: Coin) {
        navigationCommand.value =
            LandingFragmentDirections.actionLandingFragmentToCoinOverviewFragment(coin)
    }
}
