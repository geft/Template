package com.gerryjuans.template.api

import android.content.Context
import android.util.Log
import com.gerryjuans.template.base.BaseApiProvider
import com.gerryjuans.template.base.BaseApplication
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GithubRepoProvider @Inject constructor(
    private val context: Context
) : BaseApiProvider<GithubRepo>() {

    fun getRepos() : Single<List<GithubRepo>> {
        return Single.fromCallable {
            val execute = createRequestInterface(
                (context.applicationContext as BaseApplication).getBaseUrl()
            )
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