package com.gerryjuans.template.login

import com.gerryjuans.template.api.BaseApiProvider
import io.reactivex.rxjava3.core.Single

class LoginProvider : BaseApiProvider<LoginResponse>() {

    override fun getBaseUrl() = "https://reqres.in/api/"

    fun getLogin(email: String, password: String): Single<LoginResponse> {
        return get(
            createRequestInterface()
                .create(LoginService::class.java)
                .login(LoginRequest(email, password))
        )
    }
}