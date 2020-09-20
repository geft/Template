package com.gerryjuans.template.trending.error

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gerryjuans.template.R
import com.gerryjuans.template.trending.base.BaseTrendingTest
import com.gerryjuans.template.trending.base.MockResponseSuccess
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class ErrorViewTest : BaseTrendingTest() {

    override fun setResponses(mockWebServer: MockWebServer) {
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND))
    }

    @Test
    fun shouldDisplayErrorPage() {
        onView(withId(R.id.container_error)).check(matches(isDisplayed()))
        onView(withId(R.id.image_error)).check(matches(isDisplayed()))
        onView(withText(R.string.error_title)).check(matches(isDisplayed()))
        onView(withText(R.string.error_description)).check(matches(isDisplayed()))
        onView(withText(R.string.error_cta)).check(matches(isDisplayed()))
    }

    @Test
    fun onButtonClickedWithValidResponseShouldHideErrorPage() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockResponseSuccess.get)
        )
        onView(withText(R.string.error_cta)).perform(click())
        onView(withId(R.id.container_error)).check(matches(not(isDisplayed())))
    }
}