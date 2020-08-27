package com.gerryjuans.template.login

import android.content.Context
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

    private companion object {
        const val KEY = "LOGIN"
    }

    private lateinit var binding: ActivityLoginBinding

    override fun createPresenter() = LoginPresenter(
        LoginProvider(),
        getSharedPreferences(KEY, Context.MODE_PRIVATE)
    )

    override fun getActivityView() = this

    override fun onInitView() {
        binding = ActivityLoginBinding.inflate(
            LayoutInflater.from(this)
        )

        setContentView(binding.root)
        initButton()
        initEmail()
        initPassword()
    }

    private fun initButton() {
        binding.buttonAction.setThrottleListener {
            val email = binding.fieldEmail.text.toString()
            val password = binding.fieldPassword.text.toString()

            if (binding.checkbox.isChecked) {
                presenter.save(email, password)
            }

            presenter.login(email, password)
        }
    }

    private fun initEmail() {
        binding.fieldEmail.post {
            val email = presenter.loadEmail()

            if (email.isNotBlank()) {
                binding.fieldEmail.setText(email)
                binding.checkbox.isChecked = true
            }
        }
    }

    private fun initPassword() {
        binding.fieldPassword.post {
            binding.fieldPassword.setText(presenter.loadPassword())
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