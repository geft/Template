package com.gerryjuans.template.trending.usecase

import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.api.GithubRepoProvider
import com.gerryjuans.template.base.scheduler.RxScheduler
import com.gerryjuans.template.trending.TrendingView
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TrendingPopulator @Inject constructor (
    private val repoProvider: GithubRepoProvider,
    private val scheduler: RxScheduler
) {

    fun getPopulateSingle(view: TrendingView?): Single<List<GithubRepo>> =
        repoProvider.getRepos()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.main())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.stopLoading() }
            .doOnError { view?.showError() }
            .map {
                if (it.isNullOrEmpty()) throw IllegalStateException("list is null or empty")
                it
            }
}