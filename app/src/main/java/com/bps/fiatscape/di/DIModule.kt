package com.bps.fiatscape.di

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bps.fiatscape.BuildConfig
import com.bps.fiatscape.R
import com.bps.fiatscape.common.navigation.Navigator
import com.bps.fiatscape.common.network.CoinPaprikaAPI
import com.bps.fiatscape.common.network.CoinPaprikaRepo
import com.bps.fiatscape.common.network.CoinPaprikaRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Provides
    @Singleton
    fun provideNavController(@ActivityContext activity: AppCompatActivity): NavHostFragment =
        activity
            .supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    @Provides
    @Singleton
    fun provideNavigator(navController: NavController): Navigator {
        return Navigator(navController)
    }

    @Provides
    @Singleton
    fun provideBaseNetwork(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesCoinApi(retrofit: Retrofit): CoinPaprikaAPI {
        return retrofit.create(CoinPaprikaAPI::class.java)
    }

    @Provides
    @Singleton
    fun providedCoinRepo(api: CoinPaprikaAPI): CoinPaprikaRepo {
        return CoinPaprikaRepoImpl(api)
    }
}
