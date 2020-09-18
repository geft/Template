package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BaseModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class TrendingModel(
    var items: List<GithubRepo> = emptyList()
) : BaseModel()