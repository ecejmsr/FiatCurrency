package com.bps.fiatscape.common.network.favorites

import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.sharedPref.EncryptedSharePrefRepo
import com.bps.fiatscape.common.sharedPref.EncryptedSharedPrefRepoImpl.Companion.FAV_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import javax.inject.Inject

class FavoritesRepoImpl @Inject constructor(private val encryptedSharedPref: EncryptedSharePrefRepo): FavoritesRepo {
    override fun getFavoriteCoins(): MutableList<Coin> {
        val favCoinJson = encryptedSharedPref.getKeyValueString(FAV_KEY)
        return favCoinJson?.let {
            val type = object : TypeToken<MutableList<Coin>>() {}.type
            val final = Gson().fromJson<MutableList<Coin>>(favCoinJson, type) ?: mutableListOf()
            final
        } ?: mutableListOf()
    }

    override fun addFavoriteCoin(coin: Coin) {
        val coinList = getFavoriteCoins()

        coinList.add(coin)

        encryptedSharedPref.setKeyValueString(FAV_KEY, Gson().toJson(coinList))
    }

    override fun removeFavoriteCoin(coin: Coin) {
        val coinList = getFavoriteCoins()

        coinList.remove(coin)

        encryptedSharedPref.setKeyValueString(FAV_KEY, Gson().toJson(coinList))
    }

    override fun isCoinFavorite(coin: Coin?): Boolean {
        if (coin == null) return false

        val coinList = getFavoriteCoins()
        return coinList.contains(coin)
    }
}
