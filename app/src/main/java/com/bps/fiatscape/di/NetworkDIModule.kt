package com.bps.fiatscape.di

import com.bps.fiatscape.BuildConfig
import com.bps.fiatscape.common.network.CoinPaprikaAPI
import com.bps.fiatscape.common.network.CoinPaprikaRepo
import com.bps.fiatscape.common.network.CoinPaprikaRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDIModule {
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
    fun provideCoinRepo(api: CoinPaprikaAPI): CoinPaprikaRepo {
        return CoinPaprikaRepoImpl(api)
    }

}
