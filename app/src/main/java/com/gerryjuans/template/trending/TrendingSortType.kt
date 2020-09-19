package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepo

enum class TrendingSortType(val getSortedItems: (List<GithubRepo>) -> List<GithubRepo>) {
    NAME    ( { items -> items.sortedBy { it.name } } ),
    STARS   ( { items -> items.sortedByDescending { it.stars } } )
}