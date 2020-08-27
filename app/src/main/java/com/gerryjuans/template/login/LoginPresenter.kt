package com.gerryjuans.template.login

import android.util.Log
import com.gerryjuans.template.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter(
    private val loginProvider: LoginProvider
) : BasePresenter<LoginView, LoginModel>() {

    override fun createViewModel() = LoginModel()

    fun login(email: String, password: String) {
        compositeDisposable.add(
            loginProvider.getLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { view?.navigateToResult() },
                    {
                        Log.e(this::javaClass.name, "login: ", it)
                        view?.showError()
                    })
        )
    }
}