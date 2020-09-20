package com.gerryjuans.template.trending.base

import com.gerryjuans.template.base.BaseApplication

class MockApplication : BaseApplication() {

    override fun getBaseUrl() = "http://127.0.0.1:8080"
}