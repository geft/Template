package com.gerryjuans.template.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.gerryjuans.template.R
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.databinding.TrendingActivityBinding
import com.gerryjuans.template.di.buildComponent
import com.gerryjuans.template.trending.list.TrendingListAdapter
import com.gerryjuans.template.util.setThrottleListener

class TrendingActivity : BaseActivity<TrendingView, TrendingPresenter>(), TrendingView {

    private lateinit var binding: TrendingActivityBinding
    private val layoutManager = LinearLayoutManager(this)
    private lateinit var listAdapter: TrendingListAdapter

    override fun injectComponent() {
        buildComponent(this).inject(this)
    }

    override fun getActivityView() = this

    override fun onInitView(savedInstanceState: Bundle?) {
        binding = TrendingActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        listAdapter = TrendingListAdapter(this, emptyList())

        initMenu()
        initRetry()
        initRecyclerView()
        initSwipeRefresh()

        presenter.populate()
    }

    private fun initMenu() {
        binding.imageMenu.setThrottleListener {
            PopupMenu(this, binding.imageMenu).apply {
                menuInflater.inflate(R.menu.trending, this.menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.sort_stars -> presenter.sortBy(TrendingSortType.STARS)
                        R.id.sort_name -> presenter.sortBy(TrendingSortType.NAME)
                    }
                    false
                }
            }
                .show()
        }
    }

    private fun initRetry() {
        binding.containerError.setButtonClickedListener {
            hideError()
            presenter.populate()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = listAdapter
        binding.recyclerView.itemAnimator = null
    }

    private fun initSwipeRefresh() {
        binding.containerSwipe.setOnRefreshListener {
            binding.containerSwipe.isRefreshing = true
            presenter.populateFromApi()
        }
    }

    override fun updateList(items: List<GithubRepo>) {
        stopRefreshing()
        listAdapter.updateList(items)
    }

    private fun stopRefreshing() {
        binding.containerSwipe.post {
            binding.containerSwipe.isRefreshing = false
        }
    }

    override fun scrollTo(position: Int) {
        binding.recyclerView.post {
            layoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

    override fun showLoading() {
        binding.placeholder.show()
    }

    override fun stopLoading() {
        binding.placeholder.hide()
    }

    override fun showError() {
        binding.containerError.visibility = View.VISIBLE
    }

    override fun hideError() {
        binding.containerError.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()

        val scrollPosition = layoutManager.findFirstVisibleItemPosition()
        presenter.updateScrollPosition(scrollPosition)
        presenter.saveState()
    }
}