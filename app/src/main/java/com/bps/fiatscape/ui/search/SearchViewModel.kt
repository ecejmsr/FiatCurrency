package com.bps.fiatscape.ui.search

import Currency
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bps.fiatscape.common.base.BaseViewModel
import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.network.api.CoinPaprikaRepo
import com.bps.fiatscape.ui.landing.LandingFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private  val coinRepo: CoinPaprikaRepo
) : BaseViewModel(app) {

    private val _coinSearchList = MutableLiveData<List<Coin>>()
    val coinSearchList: LiveData<List<Coin>> = _coinSearchList

    fun searchCoin(coinName: String) {
        viewModelScope.launch {
            coinRepo.search(coinName).collect { response ->
                when (response) {
                    is APIResponse.Loading -> {
                        _coinSearchList.postValue(listOf())
                        _loading.postValue(true)
                        _error.postValue(null)
                    }

                    is APIResponse.Success -> {
                        _loading.postValue(false)
                        _coinSearchList.postValue(response.data.currencies)
                    }

                    is APIResponse.Error -> {
                        _loading.postValue(false)
                        _error.postValue(response.message)
                    }
                }
            }
        }
    }

    fun navigateToCoinOverview(coin: Coin) {
        navigationCommand.value =
            SearchFragmentDirections.actionSearchFragmentToCoinOverviewFragment(coin)
    }
}
