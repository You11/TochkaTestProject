package ru.you11.tochkatestproject.login

import android.content.Intent
import ru.you11.tochkatestproject.BasePresenter
import ru.you11.tochkatestproject.BaseView

interface LoginContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {

        fun loginWithVK()

        fun loginWithGoogle()

        fun setupLoginWithFacebook()

        fun callbackWithVK(requestCode: Int, resultCode: Int, data: Intent?): Boolean

        fun callbackWithFacebook(requestCode: Int, resultCode: Int, data: Intent?)

        fun startActivity()

        fun saveUserInfo()

        fun isUserLoggedIn(): Boolean
    }
}