package com.gerryjuans.template.trip

import android.content.SharedPreferences
import com.gerryjuans.template.api.BaseApiProvider
import io.reactivex.rxjava3.core.Single

class TripAddProvider(
    private val sharedPref: SharedPreferences
): BaseApiProvider<TripResponse>() {

    private companion object {
        const val KEY_FROM = "FROM"
        const val KEY_TO = "TO"
        const val KEY_DRIVER = "DRIVER"
        const val KEY_ETA = "ETA"
        const val KEY_STATUS = "STATUS"
        const val KEY_CREATE = "CREATE"
    }

    override fun getBaseUrl() = "https://crudcrud.com/api/"

    fun loadSavedTrip(): TripResponse.Data? {
        if (!sharedPref.contains(KEY_CREATE)) return null

        return TripResponse.Data(
            sharedPref.getString(KEY_FROM, "") ?: "",
            sharedPref.getString(KEY_TO, "") ?: "",
            sharedPref.getString(KEY_ETA, "") ?: "",
            sharedPref.getString(KEY_STATUS, "") ?: "",
            sharedPref.getString(KEY_DRIVER, "") ?: "",
            sharedPref.getString(KEY_CREATE, "") ?: ""
        )
    }

    fun saveTripLocally(data: TripResponse.Data) {
        sharedPref.edit()
            .putString(KEY_FROM, data.startStation)
            .putString(KEY_TO, data.endStation)
            .putString(KEY_DRIVER, data.driverName)
            .putString(KEY_STATUS, data.status)
            .putString(KEY_ETA, data.arrivalTime)
            .putString(KEY_CREATE, data.createTime)
            .apply()
    }

    fun saveTripToServer(data: TripResponse) : Single<TripResponse> {
        return get(
            createRequestInterface()
                .create(TripService::class.java)
                .saveTrip(data)
        )
    }
}