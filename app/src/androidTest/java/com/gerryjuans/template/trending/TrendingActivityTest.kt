package com.gerryjuans.template.trending

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gerryjuans.template.R
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(TrendingActivity::class.java)

    @Test
    fun titleShouldBeTrending() {
        onView(ViewMatchers.withText("Trending"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun menuButtonShouldExist() {
        onView(ViewMatchers.withId(R.id.image_menu))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickingMenuButtonShouldShowSortMenu() {
        onView(ViewMatchers.withId(R.id.image_menu)).perform(ViewActions.click())
        onView(ViewMatchers.withText("Sort by stars"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("Sort by name"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun errorContainerShouldNotShow() {
        onView(ViewMatchers.withId(R.id.container_error))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun placeholderContainerShouldNotShow() {
        onView(ViewMatchers.withId(R.id.placeholder))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }
}