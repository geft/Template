package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepoProvider
import com.gerryjuans.template.base.BasePresenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingPresenter @Inject constructor(
    private val repoProvider: GithubRepoProvider
) : BasePresenter<TrendingView, TrendingModel>() {

    override fun createViewModel() = TrendingModel()
}