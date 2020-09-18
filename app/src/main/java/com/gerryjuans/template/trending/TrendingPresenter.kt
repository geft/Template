package com.gerryjuans.template.trending

import android.os.Bundle
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
            model.load(prevData)
            view?.updateList(prevData.items)
            view?.scrollTo(prevData.scrollPosition)
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
        model.items = type.getSortedItems(model.items)
        view?.updateList(model.items)
    }

    fun saveToBundle(bundle: Bundle, scrollPosition: Int) {
        model.scrollPosition = scrollPosition
        bundle.putParcelable(KEY_MODEL, model)
        trendingProvider.save(model)
    }

    fun restoreFromBundle(bundle: Bundle) {
        bundle.getParcelable<TrendingModel>(KEY_MODEL)?.let {
            model.load(it)
        }
    }

    fun updateScrollPosition(scrollPosition: Int) {
        model.scrollPosition = scrollPosition
    }

    enum class SortType(val getSortedItems: (List<GithubRepo>) -> List<GithubRepo>) {
        NAME    ( { items -> items.sortedBy { it.name } } ),
        STARS   ( { items -> items.sortedByDescending { it.stars } } )
    }

    private companion object {
        const val KEY_MODEL = "BUNDLE"
    }
}