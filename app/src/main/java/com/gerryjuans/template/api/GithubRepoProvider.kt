package com.gerryjuans.template.api

import com.gerryjuans.template.base.BaseApiProvider
import javax.inject.Inject

class GithubRepoProvider @Inject constructor() : BaseApiProvider<GithubRepo>() {

    private companion object {
        const val BASE_URL = "https://ghapi.huchen.dev/"
    }

    override fun getBaseUrl() = BASE_URL

    fun getRepos() {

    }
}