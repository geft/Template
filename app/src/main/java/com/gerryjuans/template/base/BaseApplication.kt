package com.gerryjuans.template.base

import android.app.Application
import android.util.Log
import com.gerryjuans.template.di.AppComponent
import com.gerryjuans.template.di.DaggerAppComponent
import com.gerryjuans.template.di.ProviderModule
import com.jakewharton.threetenabp.AndroidThreeTen

class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        setupUncaughtExceptionHandling()
        initThreeTen()
        initAppComponent()
    }

    private fun setupUncaughtExceptionHandling() {
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            Log.e(this.javaClass.name, "Uncaught exception", e)
        }
    }

    private fun initThreeTen() {
        AndroidThreeTen.init(this)
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .providerModule(ProviderModule(this))
            .build()
    }
}