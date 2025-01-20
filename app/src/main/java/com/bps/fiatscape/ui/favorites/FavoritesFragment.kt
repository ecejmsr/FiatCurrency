package com.bps.fiatscape.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bps.fiatscape.R
import com.bps.fiatscape.common.base.BaseFragment
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.databinding.FragmentFavoritesBinding
import com.bps.fiatscape.ui.landing.rv.CryptoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    override val viewModel: FavoritesViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_favorites
    override var appBarNotificationsVisibility: Int = View.GONE

    private lateinit var adapter: CryptoListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservables()
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoritesList()
        viewModel.favoriteList.value?.let { adapter.updateCoins(it) }
    }

    private fun setObservables() {
        viewModel.favoriteList.observe(viewLifecycleOwner) { coinList ->
            adapter.updateCoins(coinList)
            if (coinList.size > 0) {
                binding.fragmentFavoritesEmptyText.visibility = View.GONE
            } else {
                binding.fragmentFavoritesEmptyText.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = CryptoListAdapter { coin ->
            viewModel.navigateToCoinOverview(coin)
        }
        binding.fragmentFavoritesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoritesFragment.adapter
        }
    }

}
