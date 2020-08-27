package com.gerryjuans.template.base

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BasePresenter<V: BaseView, M : BaseModel> {

    var view: V? = null

    protected val compositeDisposable = CompositeDisposable()

    protected abstract fun createViewModel(): M

    fun dispose() {
        compositeDisposable.clear()
    }

    fun clearActivityView() {
        view = null
    }

    fun setActivityView(view: V) {
        this.view = view
    }
}