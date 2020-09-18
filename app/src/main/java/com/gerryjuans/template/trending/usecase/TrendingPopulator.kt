package com.gerryjuans.template.trending.usecase

import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.api.GithubRepoProvider
import com.gerryjuans.template.trending.TrendingView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TrendingPopulator @Inject constructor (
    private val repoProvider: GithubRepoProvider
) {

    fun getPopulateDisposable(view: TrendingView?,
                              onSuccess: (List<GithubRepo>) -> Unit,
                              onError: (Throwable) -> Unit
    ): Disposable {
        return repoProvider.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.stopLoading() }
            .doOnError { view?.showError() }
            .subscribe({
                if (it.isNullOrEmpty()) throw IllegalStateException("list is null or empty")
                onSuccess(it)
            }, { onError(it) })
    }
}