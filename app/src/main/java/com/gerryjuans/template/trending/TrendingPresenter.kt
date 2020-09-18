package com.gerryjuans.template.trending

import android.util.Log
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.api.GithubRepoProvider
import com.gerryjuans.template.base.BasePresenter
import com.gerryjuans.template.trending.usecase.TrendingPopulator
import com.gerryjuans.template.trending.usecase.TrendingTimeChecker
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingPresenter @Inject constructor(
    private val repoProvider: GithubRepoProvider,
    private val trendingProvider: TrendingProvider,
    private val trendingPopulator: TrendingPopulator,
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
            trendingPopulator.getPopulateDisposable(
                view = view,
                onSuccess = { updateList(it, LocalDateTime.now()) },
                logOnError = { Log.e(this.javaClass.name, it.message, it) }
            )
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