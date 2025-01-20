package com.bps.fiatscape.landing

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bps.fiatscape.R
import com.bps.fiatscape.common.base.BaseViewModel
import com.bps.fiatscape.common.dataclasses.APIResponse
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.network.CoinPaprikaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

            refreshData()
        }
    }

    private fun refreshData() {
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        _lastRefreshed.postValue(app.getString(R.string.last_refreshed, dateFormat.format(date)))
    }
}
