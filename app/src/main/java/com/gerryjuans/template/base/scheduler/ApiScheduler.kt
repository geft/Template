package com.gerryjuans.template.base.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ApiScheduler : RxScheduler {
    override fun io(): io.reactivex.rxjava3.core.Scheduler = Schedulers.io()

    override fun computation(): io.reactivex.rxjava3.core.Scheduler = Schedulers.computation()

    override fun main(): io.reactivex.rxjava3.core.Scheduler = AndroidSchedulers.mainThread()
}