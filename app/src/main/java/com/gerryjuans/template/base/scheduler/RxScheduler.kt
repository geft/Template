package com.gerryjuans.template.base.scheduler

import io.reactivex.rxjava3.core.Scheduler

interface RxScheduler {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun main(): Scheduler
}