package com.gerryjuans.template.trending.usecase

import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class TrendingTimeChecker @Inject constructor() {

    fun isDataExpired(prevTime: LocalDateTime?, currentTime: LocalDateTime): Boolean {
        if (prevTime == null) return true

        val durationInMinutes = Duration.between(prevTime, currentTime).toMinutes()
        return durationInMinutes > TrendingConstants.REFRESH_THRESHOLD_IN_MINUTES
    }
}