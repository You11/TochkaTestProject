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
import ru.you11.tochkatestproject.model.AppUser


class LoginFragment: Fragment(), LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter

    private lateinit var vkLoginButton: Button
    private lateinit var googleLoginButton: Button
    private lateinit var facebookLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        with(root) {
            vkLoginButton = findViewById(R.id.login_vk_button)
            googleLoginButton = findViewById(R.id.login_google_button)
            facebookLoginButton = findViewById(R.id.login_facebook_button)

            vkLoginButton.setOnClickListener {
                presenter.loginWithVK()
            }

            googleLoginButton.setOnClickListener {
                presenter.loginWithGoogle()
            }

            facebookLoginButton.setOnClickListener {
                presenter.loginWithFacebook()
            }
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
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showVKErrorMessage(errorMessage: String) {
        Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
    }
}