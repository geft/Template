package com.gerryjuans.template.trending.usecase

import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class TrendingTimeChecker @Inject constructor() {

    fun isDataExpired(prevTime: LocalDateTime?, currentTime: LocalDateTime): Boolean {
        if (prevTime == null) return true

        val durationInHours = Duration.between(currentTime, prevTime).toHours()
        return durationInHours > TrendingConstants.REFRESH_THRESHOLD_IN_HOURS
    }
}