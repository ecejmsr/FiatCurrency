package com.bps.fiatscape

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bps.fiatscape.common.dataclasses.Coin
import com.bps.fiatscape.common.navigation.BottomNavigationManager
import com.bps.fiatscape.common.navigation.Navigator
import com.bps.fiatscape.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }
    private val navController by lazy {
        navHostFragment.navController
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setBinding()
        binding.lifecycleOwner = this
        setBottomNavigation()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onBackPressed() {
        if (navController.previousBackStackEntry != null) {
            backPressedFunction?.invoke() ?: navController.navigateUp()
        } else {
            super.onBackPressed()
        }
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setAppBar(
            backBtn = View.GONE,
            notificationBtn = View.GONE,
            backButtonFunction = null
        )
    }

    fun setAppBar(
        backButtonFunction: (() -> Unit)?,
        backBtn: Int,
        notificationBtn: Int,
        notificationBtnFunction: (() -> Unit)? = null
    ) {
        binding.toolbarAppbarlayoutABL.visibility =
            if (backBtn == View.VISIBLE ||
                notificationBtn == View.VISIBLE
            ) View.VISIBLE else View.GONE
        binding.toolbarNavigationNotificationBtn.visibility = notificationBtn
        binding.toolbarNavigationBackBtn.visibility = backBtn
        binding.toolbarNavigationBackBtn.setOnClickListener {
            if (backButtonFunction == null)
                onBackPressed()
            else
                backButtonFunction.invoke()
        }
        notificationBtnFunction?.let {
            binding.toolbarNavigationNotificationBtn.setOnClickListener {
                notificationBtnFunction.invoke()
            }
        }
    }

    private fun setBottomNavigation() {
        binding.bottomNavigationView.apply {

            navController.let { navController ->
                selectedItemId = R.id.homeFragment
                NavigationUI.setupWithNavController(this, navController)
                setOnItemSelectedListener { item ->
                    bottomSheetNavigation(item)
                    true
                }
            }
        }
    }

    private fun bottomSheetNavigation(item: MenuItem) {
        when (item.itemId) {
            R.id.homeFragment -> BottomNavigationManager.navigateToHome(navController)
            R.id.favoritesFragment -> BottomNavigationManager.navigateToFavorites(navController)
            R.id.searchFragment -> BottomNavigationManager.navigateToSearch(navController)
        }
    }

    fun setCoin(coin: Coin) {
        viewModel.setCoin(coin)
    }

    fun clearCoin() {
        viewModel.clearCoin()
    }

    companion object {
        var backPressedFunction: (() -> Unit)? = null
    }
}
