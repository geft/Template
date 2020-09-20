package com.gerryjuans.template.base

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.google.android.gms.security.ProviderInstaller
import com.jakewharton.threetenabp.AndroidThreeTen

open class BaseApplication : MultiDexApplication() {

    private companion object {
        const val BASE_URL = "https://ghapi.huchen.dev/"
    }

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

    open fun getBaseUrl() = BASE_URL
}