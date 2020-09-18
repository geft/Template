package com.gerryjuans.template.trending.usecase

import com.gerryjuans.template.trending.TrendingModel
import com.gerryjuans.template.trending.TrendingProvider
import javax.inject.Inject

class TrendingLoader @Inject constructor(
    private val trendingProvider: TrendingProvider,
    private val timeChecker: TrendingTimeChecker
) {

    fun load(callback: Callback) {
        if (callback.isLoaded) {
            callback.updateListAndScroll()
        } else {
            val prevData = trendingProvider.load()
            if (prevData == null || timeChecker.isDataExpired(prevData.time)) {
                callback.populateFromApi()
            } else {
                callback.updateModel(prevData)
                callback.updateListAndScroll()
                callback.isLoaded = true
            }
        }
    }

    interface Callback {
        var isLoaded: Boolean

        fun updateModel(data: TrendingModel)
        fun updateListAndScroll()
        fun populateFromApi()
    }
}