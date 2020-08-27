package com.gerryjuans.template.util

import android.util.Log
import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun View.setThrottleListener(action: () -> Unit) {
    this.clicks()
        .throttleFirst(200, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ action() }, { Log.e(this::javaClass.name, "setThrottleListener: ", it) })
}