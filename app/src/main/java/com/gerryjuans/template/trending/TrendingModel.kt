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

    fun load(data: TrendingModel) {
        this.items = data.items
        this.time = data.time
        this.scrollPosition = data.scrollPosition
    }
}