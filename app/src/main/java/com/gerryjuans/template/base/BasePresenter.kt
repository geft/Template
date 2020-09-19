package com.gerryjuans.template.base

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BasePresenter<V: BaseView> {

    var view: V? = null
    val compositeDisposable = CompositeDisposable()

    fun dispose() {
        compositeDisposable.clear()
    }
}