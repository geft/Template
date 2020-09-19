package com.gerryjuans.template.trending.error

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gerryjuans.template.R
import com.gerryjuans.template.trending.TrendingActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorViewTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(TrendingActivity::class.java)

    @Test
    fun shouldHaveNoInternetConnectionImage() {
        onView(withId(R.id.image_error))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldHaveErrorTitle() {
        onView(
            allOf(
                withText(R.string.error_title),
                withId(R.id.text_title)
            )
        )
            .check(matches(not(isDisplayed())))
    }
}