package com.gerryjuans.template.base

import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BasePresenterTest {

    private class Presenter : BasePresenter<View>()

    private interface View: BaseView

    private lateinit var presenter: Presenter

    @Before
    fun setUp() {
        presenter = Presenter()
    }

    @Test
    fun `dispose should clear composite disposable`() {
        presenter.compositeDisposable.add(Single.just(0).subscribe())
        assertEquals(1, presenter.compositeDisposable.size())
        presenter.dispose()
        assertEquals(0, presenter.compositeDisposable.size())
    }
}