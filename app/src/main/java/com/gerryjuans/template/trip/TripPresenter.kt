package com.gerryjuans.template.trip

import android.util.Log
import com.gerryjuans.template.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TripPresenter(
    private val tripProvider: TripProvider
) : BasePresenter<TripView, TripModel>() {

    override fun createViewModel() = TripModel()

    fun loadTrip() {
        compositeDisposable.add(
            tripProvider.loadTrip()
                .subscribeOn(Schedulers.io())
                .map { it.map { response -> response.toTripData() } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it == null) {
                            throw IllegalStateException("response is null")
                        } else {
                            view?.populate(it)
                        }
                    },
                    {
                        Log.e(this::javaClass.name, "login: ", it)
                        view?.showError()
                    })
        )
    }

    fun loadSavedTrip(): TripResponse.Data? {
        return tripProvider.loadSavedTrip()
    }

    fun saveTrip(data: TripResponse.Data) {
        tripProvider.saveTripLocally(data)

        compositeDisposable.add(
            tripProvider.saveTripToServer(
                TripResponse(
                    data.id,
                    data.startStation,
                    data.endStation,
                    data.arrivalTime,
                    data.status,
                    data.driverName,
                    data.createTime
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (true) {
                            view?.showTripAdded()
                        } else {
                            view?.showError()
                        }
                    },
                    {
                        Log.e(this::javaClass.name, "saveTrip: ", it)
                        view?.showError()
                    })
        )
    }
}