package com.gerryjuans.template.api

import retrofit2.Call
import retrofit2.http.GET

interface GithubRepoService {

    @GET("repositories")
    fun getRepos(request: GithubRequest): Call<List<GithubRepo>>
}