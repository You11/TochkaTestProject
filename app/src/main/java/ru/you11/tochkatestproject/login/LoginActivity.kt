package ru.you11.tochkatestproject.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.you11.tochkatestproject.R
import ru.you11.tochkatestproject.main.SearchFragment

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, "LoginFragment")
            .commit()
        LoginPresenter(fragment)
    }
}