package com.gerryjuans.template.trip

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TripService {

    @GET("bb3e04164c1c481cbeef27f8f6dea22b/trips")
    fun loadTrip(): Call<List<TripResponse>>

    @POST("bb3e04164c1c481cbeef27f8f6dea22b/trips")
    fun saveTrip(@Body request: TripResponse): Call<List<TripResponse>>
}