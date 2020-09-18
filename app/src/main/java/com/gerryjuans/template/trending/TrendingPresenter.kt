package com.gerryjuans.template.trending

import android.util.Log
import com.gerryjuans.template.api.GithubRepoProvider
import com.gerryjuans.template.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingPresenter @Inject constructor(
    private val repoProvider: GithubRepoProvider
) : BasePresenter<TrendingView, TrendingModel>() {

    override fun createViewModel() = TrendingModel()

    fun populate() {
        compositeDisposable.add(
            repoProvider.getRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading() }
                .doFinally { view?.stopLoading() }
                .doOnError { view?.showError() }
                .subscribe({
                    view?.updateList(it)
                }, { Log.e(this.javaClass.name, it.message, it) })
        )
    }
}