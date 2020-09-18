package com.gerryjuans.template.trending

import android.util.Log
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BasePresenter
import com.gerryjuans.template.trending.usecase.TrendingLoader
import com.gerryjuans.template.trending.usecase.TrendingPopulator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingPresenter @Inject constructor(
    private val trendingProvider: TrendingProvider,
    private val trendingPopulator: TrendingPopulator,
    private val trendingLoader: TrendingLoader
) : BasePresenter<TrendingView, TrendingModel>(), TrendingLoader.Callback {

    override var isLoaded = false

    override fun createViewModel() = TrendingModel()

    fun populate() {
        trendingLoader.load(this)
    }

    override fun updateModel(data: TrendingModel) {
        model.update(data)
    }

    override fun updateListAndScroll() {
        view?.updateList(model.items)
        view?.scrollTo(model.scrollPosition)
    }

    override fun populateFromApi() {
        compositeDisposable.add(
            trendingPopulator.getPopulateDisposable(
                view = view,
                onSuccess = {
                    model.refreshFromApi(it)
                    trendingProvider.save(model)
                    updateListAndScroll()
                    isLoaded = true
                },
                onError = { Log.e(this.javaClass.name, it.message, it) }
            )
        )
    }

    fun sortBy(type: SortType) {
        model.refreshFromSort(type.getSortedItems(model.items))
        updateListAndScroll()
    }

    fun updateScrollPosition(scrollPosition: Int) {
        model.scrollPosition = scrollPosition
    }

    enum class SortType(val getSortedItems: (List<GithubRepo>) -> List<GithubRepo>) {
        NAME    ( { items -> items.sortedBy { it.name } } ),
        STARS   ( { items -> items.sortedByDescending { it.stars } } )
    }
}