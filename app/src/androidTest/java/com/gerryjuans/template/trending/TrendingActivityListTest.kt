package com.gerryjuans.template.trending

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.gerryjuans.template.R
import com.gerryjuans.template.di.ProviderModule
import com.gerryjuans.template.trending.base.SuccessDispatcher
import com.gerryjuans.template.trending.base.TestUtils.withRecyclerView
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingActivityListTest {

    @get:Rule
    val activityRule = ActivityTestRule(TrendingActivity::class.java, true, false)
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.play(8080)
        mockWebServer.setDispatcher(SuccessDispatcher())
        clearSharedPref()
        activityRule.launchActivity(null)
    }

    private fun clearSharedPref() {
        InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences(ProviderModule.SHARED_PREF_KEY, Context.MODE_PRIVATE)
            .edit().clear().commit()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    private fun assertCollapsibleItems(position: Int, displayed: Boolean) {
        val assertion = if (displayed) isDisplayed() else not(isDisplayed())
        onView(recyclerView().atPositionOnView(position, R.id.container_content))
            .check(matches(assertion))
    }

    private fun recyclerView() = withRecyclerView(R.id.recycler_view)

    private fun recyclerViewItem(position: Int) = onView(recyclerView().atPosition(position))

    @Test
    fun firstItemShouldMatchMockData() {
        onView(recyclerView().atPositionOnView(0, R.id.image_avatar))
            .check(matches(isDisplayed()))
        onView(recyclerView().atPositionOnView(0, R.id.text_name))
            .check(matches(withText("test")))
            .check(matches(isDisplayed()))
        onView(recyclerView().atPositionOnView(0, R.id.text_author))
            .check(matches(withText("author")))
            .check(matches(isDisplayed()))
        onView(recyclerView().atPositionOnView(0, R.id.text_description))
            .check(matches(withText("description")))
        onView(recyclerView().atPositionOnView(0, R.id.text_language))
            .check(matches(withText("Go")))
        onView(recyclerView().atPositionOnView(0, R.id.text_fork))
            .check(matches(withText("999")))
        onView(recyclerView().atPositionOnView(0, R.id.text_star))
            .check(matches(withText("99")))

        assertCollapsibleItems(0, false)
    }

    @Test
    fun firstItemOnClickedShouldExpandHiddenData() {
        recyclerViewItem(0).perform(click())
        assertCollapsibleItems(0, true)
    }

    @Test
    fun firstItemOnClickedTwiceShouldCollapseHiddenData() {
        recyclerViewItem(0).perform(click(), click())
        assertCollapsibleItems(0, false)
    }

    @Test
    fun secondItemOnClickedAfterFirstItemOnClickedShouldExpandSecondItemAndCollapseFirstItem() {
        recyclerViewItem(1).perform(click())
        recyclerViewItem(0).perform(click())
        assertCollapsibleItems(0, true)
        assertCollapsibleItems(1, false)
    }

    @Test
    fun onRotationExpandedItemShouldStayExpanded() {
        recyclerViewItem(1).perform(click())
        assertCollapsibleItems(1, true)
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        assertCollapsibleItems(1, true)
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        assertCollapsibleItems(1, true)
    }
}