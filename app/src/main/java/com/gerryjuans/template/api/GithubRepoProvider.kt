package com.gerryjuans.template.api

import android.util.Log
import com.gerryjuans.template.base.BaseApiProvider
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GithubRepoProvider @Inject constructor() : BaseApiProvider<GithubRepo>() {

    private companion object {
        const val BASE_URL = "https://ghapi.huchen.dev/"
    }

    override fun getBaseUrl() = BASE_URL

    fun getRepos() : Single<List<GithubRepo>> {
        return Single.fromCallable {
            val execute = createRequestInterface()
                .create(GithubRepoService::class.java)
                .getRepos()
                .execute()
            Log.d(this.javaClass.name, "<REQ>: ${execute.raw().request().url()}")
            val body = execute.body()

            if (body == null) {
                throw IllegalStateException("Server down or Rate limited")
            } else {
                Log.d(this.javaClass.name, "<RES>: ${Gson().toJson(body)}")
                body
            }
        }
    }
}