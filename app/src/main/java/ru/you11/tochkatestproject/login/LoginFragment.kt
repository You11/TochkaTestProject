package ru.you11.tochkatestproject.login

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ru.you11.tochkatestproject.R
import com.facebook.FacebookException
import com.facebook.login.widget.LoginButton


class LoginFragment: Fragment(), LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        with(root) {
            val vkLoginButton = findViewById<Button>(R.id.login_vk_button)
            val googleLoginButton = findViewById<Button>(R.id.login_google_button)
            val facebookLoginButton = findViewById<LoginButton>(R.id.login_facebook_button)

            vkLoginButton.setOnClickListener {
                presenter.loginWithVK()
            }

            googleLoginButton.setOnClickListener {
                presenter.loginWithGoogle()
            }

            facebookLoginButton.setFragment(this@LoginFragment)
            presenter.setupLoginWithFacebook()
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken?) {
                presenter.startActivity()
            }

            override fun onError(error: VKError?) {
                if (error != null) showVKErrorMessage(error.errorMessage)
            }
        }))

        presenter.callbackWithFacebook(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showVKErrorMessage(errorMessage: String) {
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    fun showFacebookErrorMessage(exception: FacebookException) {
        Toast.makeText(activity, exception.localizedMessage, Toast.LENGTH_SHORT).show()
    }
}