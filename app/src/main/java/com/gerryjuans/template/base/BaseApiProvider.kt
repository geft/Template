package com.gerryjuans.template.base

import android.util.Log
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseApiProvider<R> {

    // create service after obtaining the request interface and then execute
    fun createRequestInterface(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun get(response: Call<R>): Single<R> {
        return Single.fromCallable {
            val execute = response.execute()
            Log.d(this.javaClass.name, "<REQ>: ${execute.raw().request()}")
            val body = execute.body()

            if (body == null) {
                Log.d(this.javaClass.name, "error: ${execute.message()}")
                throw IllegalStateException("Server down or Rate limited")
            } else {
                Log.d(this.javaClass.name, "<RES>: ${Gson().toJson(body)}")
                body
            }
        }
    }
}