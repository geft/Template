package com.gerryjuans.template.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepo(
    val name: String? = "",
    val avatar: String? = "",
    val author: String? = "",
    val description: String? = "",
    val language: String? = "",
    val stars: Int = 0,
    val forks: Int = 0,
    var expanded: Boolean = false
): Parcelable