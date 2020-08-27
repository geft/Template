package com.gerryjuans.template.login

import com.gerryjuans.template.base.BaseView

interface LoginView : BaseView {

    fun navigateToResult()

    fun showError()
}