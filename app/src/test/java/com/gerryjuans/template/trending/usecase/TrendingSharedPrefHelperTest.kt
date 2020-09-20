package com.gerryjuans.template.trending.usecase

import android.content.SharedPreferences
import com.gerryjuans.template.trending.TrendingModel
import com.gerryjuans.template.util.JsonProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class TrendingSharedPrefHelperTest {

    @MockK
    lateinit var jsonProvider: JsonProvider

    @MockK
    lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    private fun load() =
        TrendingSharedPrefHelper(jsonProvider, sharedPreferences).load()

    @Test
    fun `data null returns null`() {
        every { sharedPreferences.getString(any(), any()) } returns null
        assertNull(load())
    }

    @Test
    fun `data not null returns data`() {
        val json = "json"
        val model = mockk<TrendingModel>()
        every { sharedPreferences.getString(any(), any()) } returns json
        every { jsonProvider.fromJson(json, any<Class<Any>>()) } returns model
        assertEquals(model, load())
    }
}