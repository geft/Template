package com.gerryjuans.template.trending

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gerryjuans.template.R
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(TrendingActivity::class.java)

    @Test
    fun titleShouldBeTrending() {
        onView(withText(R.string.trending_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun menuButtonShouldExist() {
        onView(withId(R.id.image_menu))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickingMenuButtonShouldShowSortMenu() {
        onView(withId(R.id.image_menu)).perform(ViewActions.click())
        onView(withText(R.string.sort_stars))
            .check(matches(isDisplayed()))
        onView(withText(R.string.sort_name))
            .check(matches(isDisplayed()))
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
}