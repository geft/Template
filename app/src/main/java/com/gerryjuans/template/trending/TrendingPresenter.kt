package com.gerryjuans.template.trending

import com.gerryjuans.template.base.BasePresenter

class TrendingPresenter : BasePresenter<TrendingView, TrendingModel>() {

    override fun createViewModel() = TrendingModel()
}