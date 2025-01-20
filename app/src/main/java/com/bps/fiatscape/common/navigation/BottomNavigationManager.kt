package com.bps.fiatscape.common.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.bps.fiatscape.R

object BottomNavigationManager {

    fun navigateToHome(navController: NavController) {
        replaceFragment(navController, R.id.landingFragment)
    }

    fun navigateToFavorites(navController: NavController) {
        replaceFragment(navController, R.id.favoritesFragment)
    }

    fun navigateToSearch(navController: NavController) {
        replaceFragment(navController, R.id.searchFragment)
    }

    private fun replaceFragment(
        navController: NavController,
        fragment: Int,
        bundle: Bundle? = null
    ) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(navController.graph.startDestinationId, inclusive = false)
            .setLaunchSingleTop(true)
            .build()

        bundle?.let {
            navController.navigate(fragment, bundle, navOptions)
        } ?: navController.navigate(fragment, null, navOptions)
    }
}
