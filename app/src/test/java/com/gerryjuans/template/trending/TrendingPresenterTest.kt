package com.gerryjuans.template.trending

import com.gerryjuans.template.trending.usecase.TrendingLoader
import com.gerryjuans.template.trending.usecase.TrendingPopulator
import com.gerryjuans.template.trending.usecase.TrendingSharedPrefHelper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifyOrder
import io.mockk.verifySequence
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

    private lateinit var presenter: TrendingPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        presenter = TrendingPresenter(sharedPrefHelper, populator, loader)
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
            sharedPrefHelper.save(any())
            presenter.updateListAndScroll()
            presenter.isLoaded = true
        }
    }

    @Test
    fun `sortBy calls updateListAndScroll`() {
        presenter.sortBy(TrendingSortType.NAME)
        verifySequence { presenter.updateListAndScroll() }
    }
}