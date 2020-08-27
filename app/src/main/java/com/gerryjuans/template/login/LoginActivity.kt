package com.gerryjuans.template.login

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.gerryjuans.template.R
import com.gerryjuans.template.base.BaseActivity
import com.gerryjuans.template.databinding.ActivityLoginBinding
import com.gerryjuans.template.result.ResultActivity
import com.gerryjuans.template.util.setThrottleListener
import com.google.android.material.snackbar.Snackbar

class LoginActivity : BaseActivity<LoginView, LoginPresenter, LoginModel>(), LoginView {

    private lateinit var binding: ActivityLoginBinding

    override fun createPresenter() = LoginPresenter(LoginProvider())

    override fun getActivityView() = this

    override fun onInitView() {
        binding = ActivityLoginBinding.inflate(
            LayoutInflater.from(this)
        )

        setContentView(binding.root)
        initButton()
    }

    private fun initButton() {
        binding.buttonAction.setThrottleListener {
            presenter.login(
                binding.fieldEmail.text.toString(),
                binding.fieldPassword.text.toString()
            )
        }
    }

    override fun navigateToResult() {
        startActivity(Intent(this, ResultActivity::class.java))
    }

    override fun showError() {
        Snackbar
            .make(binding.root, R.string.error_generic, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            .show()
    }
}