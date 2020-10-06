package com.gerryjuans.template.main

import android.os.Bundle
import android.view.LayoutInflater
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.databinding.ActivityMainBinding
import com.gerryjuans.template.di.buildComponent

class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {

    private lateinit var binding: ActivityMainBinding

    override fun injectComponent() {
        buildComponent(this).inject(this)
    }

    override fun getActivityView() = this

    override fun onInitView(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )

        setContentView(binding.root)
    }

}