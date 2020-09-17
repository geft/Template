package com.gerryjuans.template.trending

import android.view.LayoutInflater
import android.view.View
import com.gerryjuans.template.api.GithubRepo
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.databinding.TrendingActivityBinding
import com.gerryjuans.template.di.AppComponent
import com.gerryjuans.template.di.buildComponent
import javax.inject.Inject

class TrendingActivity : BaseActivity<TrendingView, TrendingPresenter, TrendingModel>(), TrendingView {

    @Inject
    lateinit var appComponent: AppComponent

    private lateinit var binding: TrendingActivityBinding
    private val adapter by lazy { TrendingAdapter(this, emptyList()) }

    override fun injectComponent() {
        buildComponent().inject(this)
    }

    override fun createPresenter() = appComponent.createTrendingPresenter()

    override fun getActivityView() = this

    override fun onInitView() {
        binding = TrendingActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initRecyclerView()
        initRetry()
        presenter.populate()
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun initRetry() {
        binding.containerError.setButtonClickedListener {
            hideError()
            presenter.populate()
        }
    }

    override fun updateList(items: List<GithubRepo>) {
        adapter.setItems(items)
    }

    override fun showLoading() {
        (binding.recyclerView.adapter as? TrendingAdapter)?.setLoading()
    }

    override fun showError() {
        binding.containerError.visibility = View.VISIBLE
    }

    override fun hideError() {
        binding.containerError.visibility = View.GONE
    }
}