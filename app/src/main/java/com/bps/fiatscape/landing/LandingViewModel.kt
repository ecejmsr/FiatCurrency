package com.bps.fiatscape.landing

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bps.fiatscape.common.base.BaseViewModel
import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.network.CoinPaprikaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LandingViewModel  @Inject constructor(
    @ApplicationContext app: Context,
    coinRepo: CoinPaprikaRepo
): BaseViewModel(app) {
    private val _coinList = MutableLiveData<List<Coin>>()
    val coinList: LiveData<List<Coin>> = _coinList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepo.listCoins().collect { response ->
                when (response) {
                    is APIResponse.Loading -> {
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
        }
    }
}
