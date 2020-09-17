package com.gerryjuans.template.di

import com.gerryjuans.template.trending.TrendingActivity
import com.gerryjuans.template.trending.TrendingPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ProviderModule::class])
interface AppComponent {

    fun createTrendingPresenter(): TrendingPresenter

    fun inject(trendingActivity: TrendingActivity)
}