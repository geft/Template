package com.gerryjuans.template.trending

import com.gerryjuans.template.api.GithubRepo
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TrendingSortTypeTest {

    private lateinit var items: MutableList<GithubRepo>

    @Before
    fun setUp() {
        items = mutableListOf()
    }

    private fun run(sortType: TrendingSortType) =
        sortType.getSortedItems(items)

    private fun repo(name: String) = GithubRepo(name = name)

    private fun repo(star: Int) = GithubRepo(stars = star)

    @Test
    fun `sort by name ascending`() {
        items.addAll(listOf(
            repo("e"),
            repo("c"),
            repo("d"),
            repo("f"),
            repo("a"),
            repo("b")
        ))

        val res = run(TrendingSortType.NAME).map { it.name }.joinToString(separator = " ")
        assertEquals(
            "a b c d e f",
            res
        )
    }

    @Test
    fun `sort by stars descending`() {
        items.addAll(listOf(
            repo(5),
            repo(2),
            repo(1),
            repo(8),
            repo(4),
            repo(10)
        ))

        val res = run(TrendingSortType.STARS).map { it.stars }
        assertEquals(
            listOf(10, 8, 5, 4, 2, 1),
            res
        )
    }
}