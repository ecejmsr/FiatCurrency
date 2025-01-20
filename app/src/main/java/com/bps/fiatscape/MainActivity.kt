package com.bps.fiatscape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setBinding()
        binding.lifecycleOwner = this
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

    companion object {
        var backPressedFunction: (() -> Unit)? = null
    }
}
