package com.gerryjuans.template.trip.add

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.gerryjuans.template.databinding.WidgetTripAddBinding
import com.gerryjuans.template.trip.TripResponse
import com.gerryjuans.template.util.setThrottleListener
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class TripAddDialog(
    activity: Activity,
    private val onComplete: (TripResponse.Data) -> Unit,
    private val data: TripResponse.Data ?= null
) : Dialog(activity, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen) {

    private lateinit var binding: WidgetTripAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = WidgetTripAddBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)

        initFrom()
        initTo()
        initDriver()
        initStatus()
        initEta()
        initButton()
    }

    private fun initFrom() {
        data?.startStation?.let {
            binding.fieldFrom.post {
                binding.fieldFrom.setText(it)
            }
        }
    }

    private fun initTo() {
        data?.endStation?.let {
            binding.fieldTo.post {
                binding.fieldTo.setText(it)
            }
        }
    }

    private fun initDriver() {
        data?.driverName?.let {
            binding.fieldDriver.post {
                binding.fieldDriver.setText(it)
            }
        }
    }

    private fun initStatus() {
        val items = mutableListOf<String>()
        items.add(TripResponse.Status.PENDING.getString(context))
        items.add(TripResponse.Status.TRANSIT.getString(context))
        items.add(TripResponse.Status.COMPLETED.getString(context))

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerStatus.adapter = adapter

        data?.status?.let { status ->
            val index = items.indexOfFirst { it == status }
            if (index != -1) {
                binding.spinnerStatus.setSelection(index)
            }
        }
    }

    private fun initEta() {
        data?.arrivalTime?.let {
            binding.fieldEta.post {
                binding.fieldEta.setText(it)
            }
        }
    }

    private fun initButton() {
        binding.buttonAction.setThrottleListener {
            onComplete.invoke(
                TripResponse.Data(
                    binding.fieldFrom.text.toString(),
                    binding.fieldTo.text.toString(),
                    binding.fieldEta.text.toString(),
                    binding.spinnerStatus.selectedItem as String,
                    binding.fieldDriver.text.toString(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"))
                )
            )
            dismiss()
        }
    }
}