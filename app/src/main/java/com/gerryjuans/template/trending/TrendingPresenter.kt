package com.gerryjuans.template.trending

import android.util.Log
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BasePresenter
import com.gerryjuans.template.trending.usecase.TrendingPopulator
import com.gerryjuans.template.trending.usecase.TrendingTimeChecker
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingPresenter @Inject constructor(
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
            view?.updateList(prevData.items)
        }
    }

    fun populateFromApi() {
        compositeDisposable.add(
            trendingPopulator.getPopulateDisposable(
                view = view,
                onSuccess = {
                    updateModelAndSave(it)
                    view?.updateList(it)
                },
                logOnError = { Log.e(this.javaClass.name, it.message, it) }
            )
        )
    }

    private fun updateModelAndSave(it: List<GithubRepo>) {
        model.items = it
        model.time = LocalDateTime.now()
        trendingProvider.save(model)
    }

    fun sortBy(type: SortType) {
        view?.updateList(type.getSortedItems(model.items))
    }

    enum class SortType(val getSortedItems: (List<GithubRepo>) -> List<GithubRepo>) {
        NAME    ( { items -> items.sortedBy { it.name } } ),
        STARS   ( { items -> items.sortedByDescending { it.stars } } )
    }
}