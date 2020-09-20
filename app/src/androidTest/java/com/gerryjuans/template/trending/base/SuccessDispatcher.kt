package com.gerryjuans.template.trending.base

import android.net.Uri
import com.squareup.okhttp.mockwebserver.Dispatcher
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

class SuccessDispatcher : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        "/repositories" to MockResponseSuccess.get
    )

    override fun dispatch(request: RecordedRequest?): MockResponse {
        val errorResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        val pathWithoutQueryParams = Uri.parse(request?.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]

        return if (responseFile != null) {
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(responseFile)
        } else {
            errorResponse
        }
    }
}