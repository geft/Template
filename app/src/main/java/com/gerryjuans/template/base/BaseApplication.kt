package com.gerryjuans.template.base

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.gerryjuans.template.trending.usecase.TrendingConstants
import com.google.android.gms.security.ProviderInstaller
import com.jakewharton.threetenabp.AndroidThreeTen

open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        setupGooglePlayProvider()
        setupUncaughtExceptionHandling()
        initThreeTen()
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

    open fun getBaseUrl() = TrendingConstants.BASE_URL
}