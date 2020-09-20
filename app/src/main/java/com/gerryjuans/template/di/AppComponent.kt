package com.gerryjuans.template.di

import com.gerryjuans.template.trending.TrendingActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ProviderModule::class])
interface AppComponent {

    fun inject(trendingActivity: TrendingActivity)
}