package com.gerryjuans.template.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<V: BaseView, P : BasePresenter<V, M>, M : BaseModel> : AppCompatActivity() {

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectComponent()

        presenter = createPresenter()
        presenter.setActivityView(getActivityView())

        onInitView(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.clearActivityView()
        presenter.dispose()

        super.onDestroy()
    }

    protected abstract fun createPresenter(): P

    protected abstract fun injectComponent()

    protected abstract fun getActivityView(): V

    protected abstract fun onInitView(savedInstanceState: Bundle?)
}