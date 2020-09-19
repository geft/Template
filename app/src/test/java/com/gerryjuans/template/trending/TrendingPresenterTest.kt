package com.gerryjuans.template.trending

import com.gerryjuans.template.trending.usecase.TrendingLoader
import com.gerryjuans.template.trending.usecase.TrendingPopulator
import com.gerryjuans.template.trending.usecase.TrendingSharedPrefHelper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class TrendingPresenterTest {

    @MockK
    lateinit var sharedPrefHelper: TrendingSharedPrefHelper

    @MockK
    lateinit var populator: TrendingPopulator

    @MockK
    lateinit var loader: TrendingLoader

    @MockK
    lateinit var view: TrendingView

    @MockK
    lateinit var model: TrendingModel

    private lateinit var presenter: TrendingPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        presenter = TrendingPresenter(model, sharedPrefHelper, populator, loader)
        presenter.view = view
    }

    @Test
    fun `isLoaded is false by default`() {
        assertFalse(presenter.isLoaded)
    }

    @Test
    fun `populate calls loader`() {
        presenter.populate()
        verifySequence { loader.load(any(), any()) }
    }

    @Test
    fun `updateModel updates model`() {
        val data = mockk<TrendingModel>()
        presenter.updateModel(data)
        verifySequence { model.update(data) }
    }

    @Test
    fun `updateListAndScroll calls updateList and scrollTo`() {
        presenter.updateListAndScroll()
        verifyOrder {
            view.updateList(any())
            view.scrollTo(any())
        }
    }

    @Test
    fun `populator success will save locally, calls updateListAndScroll, and set isLoaded true`() {
        every { populator.getPopulateSingle(view) } returns Single.just(emptyList())
        presenter.populateFromApi()
        verifySequence {
            model.refreshFromApi(emptyList())
            sharedPrefHelper.save(any())
            presenter.updateListAndScroll()
            presenter.isLoaded = true
        }
    }

    @Test
    fun `sortBy calls updateListAndScroll`() {
        presenter.sortBy(TrendingSortType.NAME)
        verify {
            model.refreshFromSort(any())
            presenter.updateListAndScroll()
        }
    }

    @Test
    fun updateScrollPosition() {
        val position = 999
        presenter.updateScrollPosition(position)
        verifySequence { model.scrollPosition = position }
    }

    @Test
    fun `saveState saves via sharedPrefHelper`() {
        presenter.saveState()
        verifySequence { sharedPrefHelper.save(any()) }
    }
}