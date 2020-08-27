package com.gerryjuans.template.result

import com.gerryjuans.template.base.BasePresenter

class ResultPresenter : BasePresenter<ResultView, ResultModel>() {

    override fun createViewModel() = ResultModel()
}