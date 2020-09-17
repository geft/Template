package com.gerryjuans.template.trending

import com.gerryjuans.template.base.BaseActivity

class TrendingActivity : BaseActivity<TrendingView, TrendingPresenter, TrendingModel>(), TrendingView {

    override fun createPresenter() = TrendingPresenter()

    override fun getActivityView() = this

    override fun onInitView() {
    }
}