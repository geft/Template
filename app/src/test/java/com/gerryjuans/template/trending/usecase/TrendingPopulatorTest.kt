package com.gerryjuans.template.trending.usecase

import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.api.GithubRepoProvider
import com.gerryjuans.template.base.scheduler.TrampolineScheduler
import com.gerryjuans.template.trending.TrendingView
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifyOrder
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class TrendingPopulatorTest {

    @MockK
    lateinit var repoProvider: GithubRepoProvider

    @MockK
    lateinit var view: TrendingView

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    private fun getSingle() =
        TrendingPopulator(repoProvider, TrampolineScheduler())
            .getPopulateSingle(view)

    @Test
    fun `empty data should return throw illegal state exception`() {
        every { repoProvider.getRepos() } returns Single.just(emptyList())
        getSingle().test()
            .assertError(IllegalStateException::class.java)
            .dispose()
    }

    @Test
    fun `non-empty data should return data with no error`() {
        val data = listOf(GithubRepo())
        every { repoProvider.getRepos() } returns Single.just(data)
        getSingle().test()
            .assertValue(data)
            .dispose()
    }

    @Test
    fun `on subscribe should show loading`() {
        val data = listOf(GithubRepo())
        every { repoProvider.getRepos() } returns Single.just(data)
        getSingle().subscribe()
        verifyOrder {
            view.showLoading()
            view.stopLoading()
        }
    }
}