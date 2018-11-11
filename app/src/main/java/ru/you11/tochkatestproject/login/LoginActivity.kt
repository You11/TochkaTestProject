package ru.you11.tochkatestproject.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.you11.tochkatestproject.R

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragment = LoginFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, "LoginFragment")
            .commit()
        LoginPresenter(fragment)
    }
}