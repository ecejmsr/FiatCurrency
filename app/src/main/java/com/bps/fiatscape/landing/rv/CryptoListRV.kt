package com.bps.fiatscape.landing.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bps.fiatscape.R
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.databinding.CoinListViewHolderBinding

class CryptoListRV(private val onCoinSelected: (Coin) -> Unit) : RecyclerView.Adapter<CryptoListRV.CryptoViewHolder>() {
    private val coins = mutableListOf<Coin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = DataBindingUtil.inflate<CoinListViewHolderBinding>(
            LayoutInflater.from(parent.context),
            R.layout.coin_list_view_holder,
            parent,
            false
        )

        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(coins[position])
    }

    override fun getItemCount(): Int = coins.size

    fun updateCoins(newCoins: List<Coin>) {
        val diffCallback = CoinDiffCallback(coins, newCoins)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        coins.clear()
        coins.addAll(newCoins)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CryptoViewHolder(private val binding: CoinListViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: Coin) {
            binding.coin = coin
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onCoinSelected(coin)
            }
        }
    }

    class CoinDiffCallback(private val oldList: List<Coin>, private val newList: List<Coin>): DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


}
