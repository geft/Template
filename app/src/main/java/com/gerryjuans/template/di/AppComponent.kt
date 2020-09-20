package com.gerryjuans.template.di

import com.gerryjuans.template.trending.TrendingActivity
import dagger.Component

@Component(modules = [ProviderModule::class])
interface AppComponent {

    fun inject(trendingActivity: TrendingActivity)
}