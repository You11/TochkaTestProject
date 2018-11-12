package ru.you11.tochkatestproject.login

import android.content.Intent
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.vk.sdk.VKSdk
import ru.you11.tochkatestproject.main.MainActivity
import ru.you11.tochkatestproject.model.AppUser

class LoginPresenter(private val loginFragment: LoginFragment): LoginContract.Presenter {

    init {
        loginFragment.presenter = this
    }

    private lateinit var callbackManager: CallbackManager

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

    override fun setupLoginWithFacebook() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    startActivity()
                }

                override fun onCancel() {

                }

                override fun onError(exception: FacebookException) {
                    loginFragment.showFacebookErrorMessage(exception)
                }
            })
    }

    override fun callbackWithFacebook(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun startActivity() {
        val intent = Intent(loginFragment.activity, MainActivity::class.java)
        loginFragment.startActivity(intent)
        loginFragment.activity.finish()
    }

    override fun saveUserInfo() {

    }

    override fun isUserLoggedIn(): Boolean {
        return AppUser.isLoggedIn()
    }
}