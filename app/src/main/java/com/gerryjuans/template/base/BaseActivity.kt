package com.gerryjuans.template.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<V: BaseView, P : BasePresenter<V>> : AppCompatActivity() {

    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectComponent()

        presenter.view = getActivityView()

        onInitView(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.view = null
        presenter.dispose()

        super.onDestroy()
    }

    protected abstract fun injectComponent()

    protected abstract fun getActivityView(): V

    protected abstract fun onInitView(savedInstanceState: Bundle?)
}