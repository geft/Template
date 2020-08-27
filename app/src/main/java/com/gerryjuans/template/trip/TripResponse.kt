package com.gerryjuans.template.trip

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import com.gerryjuans.template.R
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
{
    "_id": "5f47758d9a729803e8b2d009",
    "start_station": "Surabaya",
    "end_station": "Semarang",
    "eta": "2020-08-20 00:00:00",
    "status": "Transit",
    "driver_name": "John Doe",
    "created_at": "2020-08-18 17:12:00"
}
 */
data class TripResponse(
    @SerializedName("_id") val id: String?,
    @SerializedName("start_station") val startStation: String?,
    @SerializedName("end_station") val endStation: String?,
    @SerializedName("eta") val eta: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("driver_name") val driverName: String?,
    @SerializedName("created_at") val createTime: String?
) {

    fun toTripData() = Data(
        id = id ?: "-",
        startStation = startStation ?: "-",
        endStation = endStation ?: "-",
        arrivalTime = eta ?: "-",
        status = status ?: Status.UNKNOWN.toString(),
        driverName = driverName ?: "-",
        createTime = createTime ?: "-"
    )

    @Parcelize
    data class Data(
        val id: String,
        val startStation: String,
        val endStation: String,
        val arrivalTime: String,
        val status: String,
        val driverName: String,
        val createTime: String
    ): Parcelable

    enum class Status(@StringRes val labelRes: Int?){
        UNKNOWN(null),
        PENDING(R.string.trip_status_pending),
        TRANSIT(R.string.trip_status_transit),
        COMPLETED(R.string.trip_status_completed);

        fun getString(context: Context): String {
            return if (labelRes == null) "-" else context.getString(labelRes)
        }
    }
}