package com.gerryjuans.template.trending

import android.util.Log
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.api.GithubRepoProvider
import com.gerryjuans.template.base.BasePresenter
import com.gerryjuans.template.trending.usecase.TrendingTimeChecker
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingPresenter @Inject constructor(
    private val repoProvider: GithubRepoProvider,
    private val trendingProvider: TrendingProvider,
    private val timeChecker: TrendingTimeChecker
) : BasePresenter<TrendingView, TrendingModel>() {

    override fun createViewModel() = TrendingModel()

    fun populate() {
        val prevData = trendingProvider.load()
        if (prevData == null || timeChecker.isDataExpired(prevData.time)) {
            populateFromApi()
        } else {
            updateList(prevData.items)
        }
    }

    private fun populateFromApi() {
        compositeDisposable.add(
            repoProvider.getRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading() }
                .doFinally { view?.stopLoading() }
                .doOnError { view?.showError() }
                .subscribe({
                    if (it.isNullOrEmpty()) throw IllegalStateException("list is null or empty")
                    updateList(it, LocalDateTime.now())
                }, { Log.e(this.javaClass.name, it.message, it) })
        )
    }

    private fun updateList(
        list: List<GithubRepo>, time: LocalDateTime? = null
    ) {
        model.items = list
        time?.let { model.time = it }

        view?.updateList(list)
        trendingProvider.save(model)
    }

    fun sortBy(type: SortType) {
        updateList(type.getSortedItems(model.items))
    }

    enum class SortType(val getSortedItems: (List<GithubRepo>) -> List<GithubRepo>) {
        NAME    ( { items -> items.sortedBy { it.name } } ),
        STARS   ( { items -> items.sortedByDescending { it.stars } } )
    }
}