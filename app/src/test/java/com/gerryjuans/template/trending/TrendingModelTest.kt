package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepo
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime

class TrendingModelTest {

    private lateinit var model: TrendingModel

    @Before
    fun setUp() {
        model = TrendingModel()
    }

    @Test
    fun `initial items should be empty`() {
        assertTrue(model.items.isEmpty())
    }

    @Test
    fun `initial time should be null`() {
        assertNull(model.time)
    }

    @Test
    fun `initial scroll position should be 0`() {
        assertEquals(0, model.scrollPosition)
    }

    @Test
    fun `update should replace items`() {
        val items = mockk<List<GithubRepo>>()
        val newModel = TrendingModel()
        newModel.items = items
        model.update(newModel)
        assertEquals(items, model.items)
    }

    @Test
    fun `update should replace time`() {
        val time = mockk<LocalDateTime>()
        val newModel = TrendingModel()
        newModel.time = time
        model.update(newModel)
        assertEquals(time, model.time)
    }

    @Test
    fun `update should replace scroll position`() {
        val position = 999
        val newModel = TrendingModel()
        newModel.scrollPosition = position
        model.update(newModel)
        assertEquals(position, model.scrollPosition)
    }

    @Test
    fun `refresh from api should replace items`() {
        val items = mockk<List<GithubRepo>>()
        model.refreshFromApi(items)
        assertEquals(items, model.items)
    }

    @Test
    fun `refresh from api should change time to current`() {
        model.refreshFromApi(emptyList())
        assertEquals(LocalDateTime.now(), model.time)
    }

    @Test
    fun `refresh from api should reset scroll position`() {
        model.refreshFromApi(emptyList())
        assertEquals(0, model.scrollPosition)
    }

    @Test
    fun `refresh from sort should replace items`() {
        val items = mockk<List<GithubRepo>>()
        model.refreshFromSort(items)
        assertEquals(items, model.items)
    }

    @Test
    fun `refresh from sort should not change time to current`() {
        model.refreshFromSort(emptyList())
        assertNull(model.time)
    }

    @Test
    fun `refresh from sort should reset scroll position`() {
        model.refreshFromSort(emptyList())
        assertEquals(0, model.scrollPosition)
    }
}