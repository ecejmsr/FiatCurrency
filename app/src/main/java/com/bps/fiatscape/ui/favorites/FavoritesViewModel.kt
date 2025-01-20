package com.bps.fiatscape.ui.favorites

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bps.fiatscape.common.base.BaseViewModel
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.network.favorites.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val favoritesRepo: FavoritesRepo
) : BaseViewModel(app) {

    private val _favoritesList = MutableLiveData<MutableList<Coin>>()
    val favoriteList: LiveData<MutableList<Coin>> = _favoritesList

    init {
        getFavoritesList()
    }

    fun getFavoritesList() {
        viewModelScope.launch {
            _favoritesList.postValue(favoritesRepo.getFavoriteCoins())
        }
    }

    fun navigateToCoinOverview(coin: Coin) {
        navigationCommand.value = FavoritesFragmentDirections.actionFavoritesFragmentToCoinOverviewFragment(coin)
    }
}
