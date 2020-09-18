package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BaseView

interface TrendingView : BaseView {

    fun updateList(items: List<GithubRepo>)

    fun scrollTo(position: Int)

    fun showLoading()

    fun stopLoading()

    fun showError()

    fun hideError()
}