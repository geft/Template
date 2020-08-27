package com.gerryjuans.template.login

import android.content.SharedPreferences
import android.util.Log
import com.gerryjuans.template.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter(
    private val loginProvider: LoginProvider,
    private val sharedPreferences: SharedPreferences
) : BasePresenter<LoginView, LoginModel>() {

    private companion object {
        const val KEY_EMAIL = "EMAIL"
        const val KEY_PASSWORD = "PASSWORD"
    }

    override fun createViewModel() = LoginModel()

    fun login(email: String, password: String) {
        compositeDisposable.add(
            loginProvider.getLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.token?.isNotBlank() == true) {
                            view?.navigateToResult()
                        } else {
                            view?.showError()
                        }
                    },
                    {
                        Log.e(this::javaClass.name, "login: ", it)
                        view?.showError()
                    })
        )
    }

    fun save(email: String, password: String) {
        sharedPreferences.edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .apply()
    }

    fun loadEmail(): String {
        return sharedPreferences.getString(KEY_EMAIL, "") ?: ""
    }

    fun loadPassword(): String {
        return sharedPreferences.getString(KEY_PASSWORD, "") ?: ""
    }
}