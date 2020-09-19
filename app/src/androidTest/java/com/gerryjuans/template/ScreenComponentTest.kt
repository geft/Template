package com.gerryjuans.template

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.gerryjuans.template.trending.TrendingActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ScreenComponentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(TrendingActivity::class.java)

    @Test
    fun titleShouldBeTrending() {
        onView(withText("Trending"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun menuButtonShouldExist() {
        onView(withId(R.id.image_menu))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickingMenuButtonShouldShowSortMenu() {
        onView(withId(R.id.image_menu)).perform(click())
        onView(withText("Sort by stars")).check(matches(isDisplayed()))
        onView(withText("Sort by name")).check(matches(isDisplayed()))
    }

    @Test
    fun errorContainerShouldNotShow() {
        onView(withId(R.id.container_error))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun placeholderContainerShouldNotShow() {
        onView(withId(R.id.placeholder))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun listShouldNotBeEmpty() {
//        val server = MockWebServer()
//
//        val response = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody(MockResponseFactory.getResponse())
//        server.enqueue(response)
//        server.play(8080)
//
//        onView(
//            allOf(
//                withId(R.id.recycler_view),
//                withEffectiveVisibility(Visibility.VISIBLE)
//            )
//        )
//            .check(matches(isDisplayed()))
//
//        server.shutdown()
    }
}