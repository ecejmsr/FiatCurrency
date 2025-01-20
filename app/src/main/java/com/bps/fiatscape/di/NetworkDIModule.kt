package com.bps.fiatscape.di

import android.content.Context
import com.bps.fiatscape.BuildConfig
import com.bps.fiatscape.common.network.api.CoinPaprikaAPI
import com.bps.fiatscape.common.network.api.CoinPaprikaRepo
import com.bps.fiatscape.common.network.api.CoinPaprikaRepoImpl
import com.bps.fiatscape.common.network.favorites.FavoritesRepo
import com.bps.fiatscape.common.network.favorites.FavoritesRepoImpl
import com.bps.fiatscape.common.sharedPref.EncryptedSharePrefRepo
import com.bps.fiatscape.common.sharedPref.EncryptedSharedPrefRepoImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDIModule {
    @Provides
    @Singleton
    fun provideBaseNetwork(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
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

    @Provides
    @Singleton
    fun provideEncryptedSharedPref(@ApplicationContext context: Context): EncryptedSharePrefRepo =
        EncryptedSharedPrefRepoImpl(context)

    @Provides
    @Singleton
    fun provideFavoritesRepo(encryptedSharePrefRepo: EncryptedSharePrefRepo): FavoritesRepo = FavoritesRepoImpl(encryptedSharePrefRepo)

}
