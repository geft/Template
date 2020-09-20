package com.gerryjuans.template.trending.usecase

import android.content.SharedPreferences
import com.gerryjuans.template.trending.TrendingModel
import com.gerryjuans.template.util.JsonProvider
import javax.inject.Inject

class TrendingSharedPrefHelper @Inject constructor(
    private val jsonProvider: JsonProvider,
    private val sharedPreferences: SharedPreferences
) {

    private companion object {
        const val KEY_MODEL = "MODEL"
    }

    fun save(model: TrendingModel) {
        sharedPreferences.edit()
            .putString(KEY_MODEL, jsonProvider.toJson(model))
            .apply()
    }

    fun load(): TrendingModel? {
        val data = sharedPreferences.getString(KEY_MODEL, null)
        return if (data == null) null else jsonProvider.fromJson(data, TrendingModel::class.java)
    }
}