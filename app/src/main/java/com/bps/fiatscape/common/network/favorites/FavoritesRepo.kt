package com.bps.fiatscape.common.network.favorites

import com.bps.fiatscape.common.dataclasses.Coin

interface FavoritesRepo {
    fun getFavoriteCoins(): MutableList<Coin>

    fun addFavoriteCoin(coin: Coin)

    fun removeFavoriteCoin(coin: Coin)

    fun isCoinFavorite(coin: Coin?): Boolean
}
