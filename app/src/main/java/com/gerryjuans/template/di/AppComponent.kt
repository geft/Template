package com.gerryjuans.template.di

import com.gerryjuans.template.trending.TrendingActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [PresenterComponent::class])
interface AppComponent {

    fun inject(trendingActivity: TrendingActivity)
}