package ru.you11.tochkatestproject.login

import android.content.Intent
import com.vk.sdk.VKSdk
import ru.you11.tochkatestproject.main.MainActivity
import ru.you11.tochkatestproject.model.AppUser

class LoginPresenter(private val loginFragment: LoginFragment): LoginContract.Presenter {

    init {
        loginFragment.presenter = this
    }

    override fun start() {
        if (isUserLoggedIn()) {
            startActivity()
        }
    }

    override fun loginWithVK() {
        VKSdk.login(loginFragment)
    }

    override fun loginWithGoogle() {

    }

    override fun loginWithFacebook() {

    }

    override fun startActivity() {
        val intent = Intent(loginFragment.activity, MainActivity::class.java)
        loginFragment.startActivity(intent)
    }

    override fun saveUserInfo() {

    }

    override fun isUserLoggedIn(): Boolean {
        return AppUser.isLoggedIn()
    }
}