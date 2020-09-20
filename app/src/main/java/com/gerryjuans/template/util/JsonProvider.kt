package com.gerryjuans.template.util

import com.google.gson.Gson
import javax.inject.Inject

class JsonProvider @Inject constructor(
    private val gson: Gson
) {

    fun toJson(any: Any): String {
        return gson.toJson(any)
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }
}