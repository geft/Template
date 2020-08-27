package com.gerryjuans.template.trip

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gerryjuans.template.databinding.TripListItemBinding

class TripAdapter(
    private val context: Context,
    private val items: List<TripResponse.Data>
) : RecyclerView.Adapter<TripAdapter.Holder>() {

    private val colorGreen = ContextCompat.getColor(context, android.R.color.holo_green_light)
    private val colorBlue = ContextCompat.getColor(context, android.R.color.holo_blue_light)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = TripListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return Holder(binding)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[holder.adapterPosition]

        holder.root.setBackgroundColor(if (item.status == TripResponse.Status.TRANSIT.getString(context)) colorGreen else colorBlue)
        holder.stationLabel.text = "${item.startStation} - ${item.endStation}"
        holder.driverLabel.text = item.driverName
        holder.statusLabel.text = item.status
    }

    class Holder(
        private val binding: TripListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        val root = binding.root
        val stationLabel = binding.textStation
        val driverLabel = binding.textDriver
        val statusLabel = binding.textStatus
    }
}