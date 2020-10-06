package com.gerryjuans.template.di

import com.gerryjuans.template.main.MainActivity
import dagger.Component

@Component(modules = [ProviderModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}