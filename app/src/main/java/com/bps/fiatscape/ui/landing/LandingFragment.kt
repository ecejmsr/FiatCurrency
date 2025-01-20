package com.bps.fiatscape.ui.landing

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bps.fiatscape.R
import com.bps.fiatscape.common.base.BaseFragment
import com.bps.fiatscape.databinding.FragmentLandingBinding
import com.bps.fiatscape.ui.landing.rv.CryptoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : BaseFragment<FragmentLandingBinding>() {

    override val layoutRes: Int = R.layout.fragment_landing
    override val viewModel: LandingViewModel by viewModels()
    private lateinit var adapter: CryptoListAdapter
    override var appBarNotificationsVisibility: Int = View.GONE
    override var appBarBackButtonVisibility: Int = View.GONE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpObservables()
        viewModel.coinList.value?.let { adapter.updateCoins(it) }
    }

    private fun setUpRecyclerView() {
        adapter = CryptoListAdapter { coin ->
            viewModel.navigateToCoinOverview(coin)
        }
        binding.fragmentLandingRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@LandingFragment.adapter
        }
    }

    private fun setUpObservables() {
        viewModel.coinList.observe(viewLifecycleOwner) { coinList ->
            adapter.updateCoins(coinList)
        }

        viewModel.lastRefreshed.observe(viewLifecycleOwner) { refresh ->
            binding.fragmentLandingRefreshDate.text = refresh
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                showErrorDialog()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.fragmentLandingProgressBar.visibility = View.VISIBLE
            } else {
                binding.fragmentLandingProgressBar.visibility = View.GONE
            }
        }
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Error when querying the server. Services may be unavailable.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}
