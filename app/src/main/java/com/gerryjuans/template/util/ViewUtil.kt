package com.gerryjuans.template.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun View.setThrottleListener(action: () -> Unit) {
    this.clicks()
        .throttleFirst(200, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ action() }, { Log.e(this::javaClass.name, "setThrottleListener: ", it) })
}

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url)
        .into(this)
}