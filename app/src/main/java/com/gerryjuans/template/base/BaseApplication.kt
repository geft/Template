package com.gerryjuans.template.base

import android.app.Application
import android.util.Log
import com.gerryjuans.template.di.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen

class BaseApplication : Application() {

    val appComponent = DaggerAppComponent.builder().build()

    override fun onCreate() {
        super.onCreate()

        setupUncaughtExceptionHandling()
        initThreeTen()
    }

    private fun setupUncaughtExceptionHandling() {
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            Log.e(this.javaClass.name, "Uncaught exception", e)
        }
    }

    private fun initThreeTen() {
        AndroidThreeTen.init(this)
    }
}