package com.bps.fiatscape.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bps.fiatscape.R
import com.bps.fiatscape.common.base.BaseFragment
import com.bps.fiatscape.databinding.FragmentSearchBinding
import com.bps.fiatscape.ui.landing.rv.CryptoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val viewModel: SearchViewModel by viewModels()
    override var appBarNotificationsVisibility: Int = View.GONE
    override val layoutRes: Int = R.layout.fragment_search
    private lateinit var adapter: CryptoListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpObservables()
    }

    private fun setUpObservables() {
        binding.fragmentSearchSearchBar.setEndIconOnClickListener {
            viewModel.searchCoin(binding.fragmentSearchSearchBar.editText?.text.toString())
        }

        viewModel.coinSearchList.observe(viewLifecycleOwner) {
            adapter.updateCoins(it)
        }
    }

    private fun setUpRecyclerView() {
        adapter = CryptoListAdapter { coin ->
            viewModel.navigateToCoinOverview(coin)
        }
        binding.fragmentSearchRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
        }
    }

}
