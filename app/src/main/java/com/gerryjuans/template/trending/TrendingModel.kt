package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BaseModel
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime

@Parcelize
class TrendingModel(
    var items: List<GithubRepo> = emptyList(),
    var time: LocalDateTime? = null,
    var scrollPosition: Int = 0
) : BaseModel() {

    fun update(data: TrendingModel) {
        this.items = data.items
        this.time = data.time
        this.scrollPosition = data.scrollPosition
    }

    fun refreshFromApi(items: List<GithubRepo>) {
        this.items = items
        this.time = LocalDateTime.now()
        this.scrollPosition = 0
    }

    fun refreshFromSort(items: List<GithubRepo>) {
        this.items = items
        this.scrollPosition = 0
    }
}