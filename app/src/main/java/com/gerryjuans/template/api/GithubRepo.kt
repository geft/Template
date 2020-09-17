package com.gerryjuans.template.api

data class GithubRepo(
    val name: String?,
    val avatar: String?,
    val author: String?,
    val description: String?,
    val language: String?,
    val stars: Int,
    val forks: Int
)