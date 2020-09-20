package com.gerryjuans.template.trending.usecase

import com.gerryjuans.template.trending.TrendingModel
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class TrendingLoader @Inject constructor(
    private val sharedPrefHelper: TrendingSharedPrefHelper,
    private val timeChecker: TrendingTimeChecker
) {

    fun load(callback: Callback, currentTime: LocalDateTime) {
        val prevData = sharedPrefHelper.load()
        if (prevData == null || timeChecker.isDataExpired(prevData.time, currentTime)) {
            callback.populateFromApi()
        } else {
            callback.updateModel(prevData)
            callback.updateListAndScroll()
        }
    }

    interface Callback {
        fun updateModel(data: TrendingModel)
        fun updateListAndScroll()
        fun populateFromApi()
    }
}