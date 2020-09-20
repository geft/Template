package com.gerryjuans.template.base

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.gerryjuans.template.di.AppComponent
import com.gerryjuans.template.di.DaggerAppComponent
import com.gerryjuans.template.di.ProviderModule
import com.google.android.gms.security.ProviderInstaller
import com.jakewharton.threetenabp.AndroidThreeTen

class BaseApplication : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        setupGooglePlayProvider()
        setupUncaughtExceptionHandling()
        initThreeTen()
        initAppComponent()
    }

    // for Kitkat
    private fun setupGooglePlayProvider() {
        try {
            ProviderInstaller.installIfNeeded(applicationContext);
        } catch (e: Exception) {
            Log.e(this.javaClass.name, "Error installing provider", e)
        }
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