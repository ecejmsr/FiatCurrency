package com.bps.fiatscape.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import javax.inject.Singleton

@Singleton
class Navigator(private val navController: NavController) {

    fun navigateAction(navDirection: NavDirections) {
        navController.navigate(navDirection)
    }

    fun navigateUp(): Boolean {
        return navController.navigateUp()
    }
}
