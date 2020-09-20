package com.gerryjuans.template.trending

import android.util.Log
import com.gerryjuans.template.base.BasePresenter
import com.gerryjuans.template.trending.usecase.TrendingLoader
import com.gerryjuans.template.trending.usecase.TrendingPopulator
import com.gerryjuans.template.trending.usecase.TrendingSharedPrefHelper
import com.gerryjuans.template.util.TimeProvider
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class TrendingPresenter @Inject constructor(
    private val model: TrendingModel,
    private val sharedPrefHelper: TrendingSharedPrefHelper,
    private val populator: TrendingPopulator,
    private val loader: TrendingLoader,
    private val timeProvider: TimeProvider
) : BasePresenter<TrendingView>(), TrendingLoader.Callback {

    fun populate() {
        loader.load(this, LocalDateTime.now())
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
            populator.getPopulateSingle(view)
                .subscribe({
                    model.refreshFromApi(it, timeProvider)
                    sharedPrefHelper.save(model)
                    updateListAndScroll()
                }, { Log.e(this.javaClass.name, it.message, it) })
        )
    }

    fun sortBy(type: TrendingSortType) {
        model.refreshFromSort(type.getSortedItems(model.items))
        updateListAndScroll()
    }

    fun updateScrollPosition(scrollPosition: Int) {
        model.scrollPosition = scrollPosition
    }

    fun saveState() {
        sharedPrefHelper.save(model)
    }
}