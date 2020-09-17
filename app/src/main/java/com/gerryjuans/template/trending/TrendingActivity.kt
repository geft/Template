package com.gerryjuans.template.trending

import android.view.LayoutInflater
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.databinding.TrendingActivityBinding
import com.gerryjuans.template.di.AppComponent
import com.gerryjuans.template.di.buildComponent
import javax.inject.Inject

class TrendingActivity : BaseActivity<TrendingView, TrendingPresenter, TrendingModel>(), TrendingView {

    @Inject
    lateinit var appComponent: AppComponent

    private lateinit var binding: TrendingActivityBinding

    override fun injectComponent() {
        buildComponent().inject(this)
    }

    override fun createPresenter() = appComponent.createTrendingPresenter()

    override fun getActivityView() = this

    override fun onInitView() {
        binding = TrendingActivityBinding.inflate(
            LayoutInflater.from(this)
        )

        setContentView(binding.root)
    }
}