package com.gerryjuans.template.result

import android.view.LayoutInflater
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.databinding.ActivityResultBinding

class ResultActivity : BaseActivity<ResultView, ResultPresenter, ResultModel>(), ResultView {

    private lateinit var binding: ActivityResultBinding

    override fun createPresenter() = ResultPresenter()

    override fun getActivityView() = this

    override fun onInitView() {
        binding = ActivityResultBinding.inflate(
            LayoutInflater.from(this)
        )

        setContentView(binding.root)
    }
}