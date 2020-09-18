package com.gerryjuans.template.di

import android.content.Context
import android.content.SharedPreferences
import com.gerryjuans.template.api.GithubRepoProvider
import com.gerryjuans.template.trending.TrendingProvider
import com.gerryjuans.template.trending.usecase.TrendingTimeChecker
import dagger.Module
import dagger.Provides

@Module
class ProviderModule(
    private val context: Context
) {

    private companion object {
        const val SHARED_PREF_KEY = "SHARED_PREF_KEY"
    }

    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideSharedPreferences(): SharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)

    @Provides
    fun provideTrendingProvider(sharedPreferences: SharedPreferences) = TrendingProvider(sharedPreferences)

    @Provides
    fun provideGithubRepoProvider() = GithubRepoProvider()

    @Provides
    fun provideTrendingTimeChecker() = TrendingTimeChecker()
}