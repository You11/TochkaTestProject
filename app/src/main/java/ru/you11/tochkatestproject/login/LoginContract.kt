package ru.you11.tochkatestproject.login

import ru.you11.tochkatestproject.BasePresenter
import ru.you11.tochkatestproject.BaseView

interface LoginContract {

    interface View: BaseView<Presenter> {

        fun showGoogleAuthForm()

        fun showFacebookAuthForm()

        fun showVkAuthForm()

        fun hideAuthForm()

        fun showLoginButton()
    }

    interface Presenter: BasePresenter {

        fun login()

    }
}