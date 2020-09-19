package com.gerryjuans.template.base.scheduler

import io.reactivex.rxjava3.schedulers.Schedulers

class TrampolineScheduler : RxScheduler {
    override fun io(): io.reactivex.rxjava3.core.Scheduler = Schedulers.trampoline()

    override fun computation(): io.reactivex.rxjava3.core.Scheduler = Schedulers.trampoline()

    override fun main(): io.reactivex.rxjava3.core.Scheduler = Schedulers.trampoline()
}