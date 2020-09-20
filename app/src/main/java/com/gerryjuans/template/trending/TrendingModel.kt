package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.util.TimeProvider
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class TrendingModel @Inject constructor() {

    var items: List<GithubRepo> = emptyList()
    var time: LocalDateTime? = null
    var scrollPosition: Int = 0

    fun update(data: TrendingModel) {
        this.items = data.items
        this.time = data.time
        this.scrollPosition = data.scrollPosition
    }

    fun refreshFromApi(items: List<GithubRepo>, timeProvider: TimeProvider) {
        this.items = items
        this.time = timeProvider.getCurrentTime()
        this.scrollPosition = 0
    }

    fun refreshFromSort(items: List<GithubRepo>) {
        this.items = items
        this.scrollPosition = 0
    }
}