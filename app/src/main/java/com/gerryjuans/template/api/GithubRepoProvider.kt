package com.gerryjuans.template.api

class GithubRepoProvider : BaseApiProvider<GithubRepo>() {

    private companion object {
        const val BASE_URL = "https://ghapi.huchen.dev/"
    }

    override fun getBaseUrl() = BASE_URL
}