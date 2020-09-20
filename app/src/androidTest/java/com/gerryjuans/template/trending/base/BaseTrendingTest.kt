package com.gerryjuans.template.trending.base

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.gerryjuans.template.di.ProviderModule
import com.gerryjuans.template.trending.TrendingActivity
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseTrendingTest {

    @get:Rule
    val activityRule = ActivityTestRule(TrendingActivity::class.java, true, false)

    protected val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.play(8080)
        setResponses(mockWebServer)
        clearSharedPref()
        activityRule.launchActivity(null)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    protected abstract fun setResponses(mockWebServer: MockWebServer)

    private fun clearSharedPref() {
        InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences(ProviderModule.SHARED_PREF_KEY, Context.MODE_PRIVATE)
            .edit().clear().commit()
    }
}