package com.gerryjuans.template.trending.usecase

import com.gerryjuans.template.trending.TrendingModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

class TrendingLoaderTest {

    @MockK
    lateinit var callback: TrendingLoader.Callback

    @MockK
    lateinit var sharedPrefHelper: TrendingSharedPrefHelper

    @MockK
    lateinit var timeChecker: TrendingTimeChecker

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    private val currentTime = LocalDateTime.of(
        LocalDate.of(2020, 9, 19),
        LocalTime.of(18, 0)
    )

    private fun run() =
        TrendingLoader(sharedPrefHelper, timeChecker).load(callback, currentTime)

    @Test
    fun `if is loaded then update list only`() {
        every { callback.isLoaded } returns true
        run()
        verifySequence {
            callback.isLoaded
            callback.updateListAndScroll()
        }
    }

    @Test
    fun `if not loaded and prev data null should call api`() {
        every { callback.isLoaded } returns false
        every { sharedPrefHelper.load() } returns null
        run()
        verifySequence {
            callback.isLoaded
            sharedPrefHelper.load()
            callback.populateFromApi()
        }
    }

    @Test
    fun `if not loaded and prev data valid but expired should call api`() {
        every { callback.isLoaded } returns false
        every { sharedPrefHelper.load() } returns mockk(relaxed = true)
        every { timeChecker.isDataExpired(any(), any()) } returns true
        run()
        verifySequence {
            callback.isLoaded
            sharedPrefHelper.load()
            timeChecker.isDataExpired(any(), any())
            callback.populateFromApi()
        }
    }

    @Test
    fun `if not loaded and prev data valid should update model, list, and set load flag to true`() {
        val prevData = mockk<TrendingModel>(relaxed = true)
        every { callback.isLoaded } returns false
        every { sharedPrefHelper.load() } returns prevData
        every { timeChecker.isDataExpired(any(), any()) } returns false
        run()
        verifySequence {
            callback.isLoaded
            sharedPrefHelper.load()
            timeChecker.isDataExpired(any(), any())
            callback.updateModel(prevData)
            callback.updateListAndScroll()
            callback.isLoaded = true
        }
    }
}