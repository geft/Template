package com.gerryjuans.template.trip

import android.content.Context
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.gerryjuans.template.R
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.databinding.ActivityTripBinding
import com.gerryjuans.template.trip.add.TripAddDialog
import com.gerryjuans.template.util.setThrottleListener
import com.google.android.material.snackbar.Snackbar

class TripActivity : BaseActivity<TripView, TripPresenter, TripModel>(), TripView {

    private companion object {
        const val KEY_TRIP = "TRIP"
    }

    private lateinit var binding: ActivityTripBinding

    override fun createPresenter(): TripPresenter {
        val sharedPref = getSharedPreferences(KEY_TRIP, Context.MODE_PRIVATE)
        return TripPresenter(
            TripProvider(),
            TripAddProvider(sharedPref)
        )
    }

    override fun getActivityView() = this

    override fun onInitView() {
        binding = ActivityTripBinding.inflate(
            LayoutInflater.from(this)
        )

        setContentView(binding.root)
        initAddTrip()
        binding.root.post { presenter.loadTrip() }
    }

    private fun initAddTrip() {
        binding.textAdd.setThrottleListener {
            TripAddDialog(this, {
                presenter.saveTrip(it)
            }, presenter.loadSavedTrip()).show()
        }
    }

    override fun populate(list: List<TripResponse.Data>) {
        binding.recyclerView.adapter = TripAdapter(this, list)
    }

    override fun repopulate() {
        presenter.loadTrip()
    }

    override fun showTripAdded() {
        Snackbar
            .make(binding.root, R.string.trip_add_success, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, android.R.color.holo_green_light))
            .show()
    }

    override fun showError() {
        Snackbar
            .make(binding.root, R.string.error_generic, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            .show()
    }
}