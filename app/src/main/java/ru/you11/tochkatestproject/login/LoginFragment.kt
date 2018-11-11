package ru.you11.tochkatestproject.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.you11.tochkatestproject.R
import ru.you11.tochkatestproject.main.MainContract


class LoginFragment: Fragment(), LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun showGoogleAuthForm() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFacebookAuthForm() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showVkAuthForm() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideAuthForm() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoginButton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}