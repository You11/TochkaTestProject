package ru.you11.tochkatestproject.login

import ru.you11.tochkatestproject.BasePresenter
import ru.you11.tochkatestproject.BaseView

interface LoginContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {

        fun loginWithVK()

        fun loginWithGoogle()

        fun loginWithFacebook()

        fun startActivity()

        fun saveUserInfo()

        fun isUserLoggedIn(): Boolean
    }
}