package com.gerryjuans.template.trip

import com.gerryjuans.template.base.BaseView

interface TripView : BaseView {

    fun populate(list: List<TripResponse.Data>)

    fun showTripAdded();

    fun showError()
}