package com.gerryjuans.template.base

import android.app.Application
import android.util.Log

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupUncaughtExceptionHandling()
    }

    private fun setupUncaughtExceptionHandling() {
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            Log.e(this.javaClass.name, "Uncaught exception", e)
        }
    }
}