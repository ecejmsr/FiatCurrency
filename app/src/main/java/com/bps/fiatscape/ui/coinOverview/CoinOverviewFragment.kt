package com.bps.fiatscape.ui.coinOverview

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bps.fiatscape.MainActivity
import com.bps.fiatscape.R
import com.bps.fiatscape.common.base.BaseFragment
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.databinding.FragmentCoinOverviewBinding
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinOverviewFragment : BaseFragment<FragmentCoinOverviewBinding>() {

    override val viewModel: CoinOverviewViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_coin_overview
    override var appBarBackButtonVisibility: Int = View.VISIBLE
    override var appBarNotificationsVisibility: Int = View.VISIBLE
    private val args: CoinOverviewFragmentArgs by navArgs()
    private lateinit var coin: Coin
    private val modelProducer = CartesianChartModelProducer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coin = args.coin
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.coin = coin
        viewModel.coin = coin
        (requireActivity() as MainActivity).setCoin(coin)

        initObservables()
        coin.id?.let {
            viewModel.fetchChartData(it, "2019-01-01", "2019-01-20")
            viewModel.fetchTickerData()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).clearCoin()
    }

    private fun initObservables() {
        viewModel.chartData.observe(viewLifecycleOwner) { closePrices ->
            updateChart(closePrices)
        }

        viewModel.currUSDPrice.observe(viewLifecycleOwner) { price ->
            binding.fragmentCoinOverviewRankPriceText.text = getString(R.string.current_price, price.toString())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showErrorDialog()
            binding.fragmentCoinOverviewGraphError.visibility = View.VISIBLE
        }

        viewModel.lastRefreshed.observe(viewLifecycleOwner) { refresh ->
            binding.fragmentCoinOverviewLastRefreshed.text = refresh
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.fragmentCoinOverviewProgressBar.visibility = View.VISIBLE
            } else {
                binding.fragmentCoinOverviewProgressBar.visibility = View.GONE
            }
        }

        viewModel.loadingPrice.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.fragmentCoinOverviewPriceRefreshProgress.visibility = View.VISIBLE
            } else {
                binding.fragmentCoinOverviewPriceRefreshProgress.visibility = View.GONE
            }
        }
    }

    private fun updateChart(closePrices: List<Float>) {
        lifecycleScope.launch {
            modelProducer.runTransaction {
                lineSeries {
                    series(closePrices)
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
        (requireActivity() as MainActivity).clearCoin()
    }
}
