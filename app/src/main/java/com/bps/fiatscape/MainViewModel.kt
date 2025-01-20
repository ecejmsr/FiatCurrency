package com.bps.fiatscape

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bps.fiatscape.common.base.BaseViewModel
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.network.favorites.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val favRepo: FavoritesRepo
): BaseViewModel(app) {
    private var coin: Coin? = null

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _favoriteIcon = MutableLiveData(R.drawable.hear_minus)
    val favoriteIcon: LiveData<Int> = _favoriteIcon

    fun setCoin(coin: Coin) {
        this.coin = coin
        isCoinFavorite()
    }

    fun clearCoin() {
        this.coin = null
    }

    private fun isCoinFavorite() {
        if (favRepo.isCoinFavorite(coin)) {
            _favoriteIcon.value = R.drawable.hear_minus
        } else {
            _favoriteIcon.value = R.drawable.heart_plus
        }
    }

    fun toggleFavorite() {
        Timber.d("Zelda toggle fav coin is $coin")
        coin?.let { coin ->
            if (_isFavorite.value == true) {
                removeCoinFromFavorites(coin)
                _isFavorite.value = false
                _favoriteIcon.value = R.drawable.heart_plus
            } else {
                addCoinToFavorites(coin)
                _isFavorite.value = true
                _favoriteIcon.value = R.drawable.hear_minus
            }
        }
    }

    private fun addCoinToFavorites(coin: Coin) {
        favRepo.addFavoriteCoin(coin)
    }

    private fun removeCoinFromFavorites(coin: Coin) {
        favRepo.removeFavoriteCoin(coin)
    }
}
