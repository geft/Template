package com.gerryjuans.template.trending.usecase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

class TrendingTimeCheckerTest {

    private val currentTime = LocalDateTime.of(
        LocalDate.of(2020, 9, 19),
        LocalTime.of(18, 0)
    )

    private fun run(prevTime: LocalDateTime?, currentTime: LocalDateTime) =
        TrendingTimeChecker().isDataExpired(prevTime, currentTime)

    @Test
    fun `prevTime null should return null`() {
        assertTrue(run(null, currentTime))
    }

    @Test
    fun `diff time is less than threshold should return false`() {
        val prevTime = currentTime.minusMinutes(1)
        assertFalse(run(prevTime, currentTime))
    }

    @Test
    fun `diff time is equal to threshold should return false`() {
        assertFalse(run(currentTime, currentTime))
    }

    @Test
    fun `diff time is more than threshold should return true`() {
        val diffMinutes = TrendingConstants.REFRESH_THRESHOLD_IN_MINUTES + 1
        val prevTime = currentTime.minusMinutes(diffMinutes.toLong())
        assertTrue(run(prevTime, currentTime))
    }
}