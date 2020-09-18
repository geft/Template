package com.gerryjuans.template.trending.usecase

import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class TrendingTimeChecker @Inject constructor() {

    private companion object {
        const val THRESHOLD_IN_HOURS = 2
    }

    fun isDataExpired(prevTime: LocalDateTime?): Boolean {
        if (prevTime == null) return true

        val currentTime = LocalDateTime.now()
        val durationInHours = Duration.between(currentTime, prevTime).toHours()
        return durationInHours > THRESHOLD_IN_HOURS
    }
}