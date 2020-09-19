package com.gerryjuans.template.trending.usecase

import android.content.SharedPreferences
import com.gerryjuans.template.trending.TrendingModel
import com.google.gson.Gson
import javax.inject.Inject

class TrendingSharedPrefHelper @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    private companion object {
        const val KEY_MODEL = "MODEL"
    }

    fun save(model: TrendingModel) {
        sharedPreferences.edit()
            .putString(KEY_MODEL, gson.toJson(model))
            .apply()
    }

    fun load(): TrendingModel? {
        val data = sharedPreferences.getString(KEY_MODEL, null)
        if (data == null) {
            return null
        } else {
            return gson.fromJson(data, TrendingModel::class.java)
        }
    }
}