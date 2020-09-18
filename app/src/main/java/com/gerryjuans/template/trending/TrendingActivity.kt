package com.gerryjuans.template.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerryjuans.template.R
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.databinding.TrendingActivityBinding
import com.gerryjuans.template.di.AppComponent
import com.gerryjuans.template.di.buildComponent
import com.gerryjuans.template.util.setThrottleListener
import javax.inject.Inject

class TrendingActivity : BaseActivity<TrendingView, TrendingPresenter, TrendingModel>(), TrendingView {

    @Inject
    lateinit var appComponent: AppComponent

    private lateinit var binding: TrendingActivityBinding

    override fun injectComponent() {
        buildComponent(this).inject(this)
    }

    override fun createPresenter() = appComponent.createTrendingPresenter()

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
            presenter.populate()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = TrendingAdapter(this, emptyList())
        binding.recyclerView.itemAnimator = null
        (binding.recyclerView.layoutManager as LinearLayoutManager).let {
            binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        presenter.updateScrollPosition(it.findFirstVisibleItemPosition())
                    }
                }
            })
        }
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
            if ((position <= (it.adapter as TrendingAdapter).itemCount - 1)) {
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
        val scrollPosition = (binding.recyclerView.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
        presenter.saveToBundle(outState, scrollPosition)
    }
}