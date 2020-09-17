package com.gerryjuans.template.di

import com.gerryjuans.template.trending.TrendingPresenter
import dagger.Component

@Component(modules = [ProviderModule::class])
interface PresenterComponent {

    fun createTrendingPresenter(): TrendingPresenter
}