package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BaseView

interface TrendingView : BaseView {

    fun updateList(items: List<GithubRepo>)

    fun showLoading()

    fun showError()

    fun hideError()
}