package com.gerryjuans.template.trip

import com.gerryjuans.template.api.BaseApiProvider
import io.reactivex.rxjava3.core.Single

class TripProvider : BaseApiProvider<List<TripResponse>>() {

    override fun getBaseUrl() = "https://crudcrud.com/api/"

    fun loadTrip(): Single<List<TripResponse>> {
        return get(
            createRequestInterface()
                .create(TripService::class.java)
                .loadTrip()
        )
    }
}