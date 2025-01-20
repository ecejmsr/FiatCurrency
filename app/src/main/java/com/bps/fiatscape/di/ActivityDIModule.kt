package com.bps.fiatscape.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bps.fiatscape.R
import com.bps.fiatscape.common.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityDIModule {
    @Provides
    @ActivityScoped
    fun provideNavController(activity: Activity): NavController {
        val appCompatActivity = activity as AppCompatActivity
        val navHostFragment = appCompatActivity
            .supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    @Provides
    @ActivityScoped
    fun provideNavigator(navController: NavController): Navigator {
        return Navigator(navController)
    }
}
