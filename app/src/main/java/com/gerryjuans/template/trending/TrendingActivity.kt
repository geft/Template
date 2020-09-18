package com.gerryjuans.template.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.gerryjuans.template.R
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.base.BaseApplication
import com.gerryjuans.template.databinding.TrendingActivityBinding
import com.gerryjuans.template.di.buildComponent
import com.gerryjuans.template.util.setThrottleListener

class TrendingActivity : BaseActivity<TrendingView, TrendingPresenter, TrendingModel>(), TrendingView {

    private lateinit var binding: TrendingActivityBinding

    override fun injectComponent() {
        buildComponent(this).inject(this)
    }

    override fun createPresenter() =
        (applicationContext as BaseApplication).appComponent.createTrendingPresenter()

    override fun getActivityView() = this

    override fun onInitView(savedInstanceState: Bundle?) {
        binding = TrendingActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initMenu()
        initRetry()
        initRecyclerView()
        initSwipeRefresh()

        savedInstanceState?.let { presenter.restoreFromBundle(it) }
        presenter.populate()
    }

    private fun initMenu() {
        binding.imageMenu.setThrottleListener {
            PopupMenu(this, binding.imageMenu).apply {
                menuInflater.inflate(R.menu.trending, this.menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.sort_stars -> presenter.sortBy(TrendingPresenter.SortType.STARS)
                        R.id.sort_name -> presenter.sortBy(TrendingPresenter.SortType.NAME)
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
            presenter.populateFromApi()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = TrendingAdapter(this, emptyList())
        binding.recyclerView.itemAnimator = null
    }

    private fun initSwipeRefresh() {
        binding.containerSwipe.setOnRefreshListener {
            binding.containerSwipe.isRefreshing = true
            presenter.populateFromApi()
        }
    }

    override fun updateList(items: List<GithubRepo>) {
        (binding.recyclerView.adapter as TrendingAdapter).updateList(items)
        binding.containerSwipe.isRefreshing = false
    }

    override fun scrollTo(position: Int) {
        binding.recyclerView.let {
            if ((position < (it.adapter as TrendingAdapter).itemCount)) {
                (it.layoutManager as LinearLayoutManager).scrollToPosition(position)
            }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        presenter.saveToBundle(outState)
    }

    override fun onPause() {
        super.onPause()

        val scrollPosition = (binding.recyclerView.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
        presenter.updateScrollPosition(scrollPosition)
    }
}