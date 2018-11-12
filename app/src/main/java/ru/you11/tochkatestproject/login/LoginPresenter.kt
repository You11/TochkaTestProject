package ru.you11.tochkatestproject.login

import android.content.Intent
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ru.you11.tochkatestproject.main.MainActivity
import ru.you11.tochkatestproject.model.AppUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginPresenter(private val loginFragment: LoginFragment): LoginContract.Presenter {

    init {
        loginFragment.presenter = this
    }

    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient
    private val GOOGLE_SIGN_IN = 100

    override fun start() {
        if (isUserLoggedIn()) {
            startActivity()
        }
        setupGoogleSignInClient()
    }

    override fun loginWithVK() {
        VKSdk.login(loginFragment)
    }

    //i don't know why exactly this needs to return boolean but it's their docs so ok
    override fun callbackWithVK(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return (VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                override fun onResult(res: VKAccessToken?) {
                    startActivity()
                }

                override fun onError(error: VKError?) {
                    if (error != null) loginFragment.showVKErrorMessage(error.errorMessage)
                }
            }))
    }

    private fun setupGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(loginFragment.activity, gso)
    }

    override fun loginWithGoogle() {
        val intent = googleSignInClient.signInIntent
        loginFragment.startActivityForResult(intent, GOOGLE_SIGN_IN)
    }

    override fun callbackWithGoogle(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != GOOGLE_SIGN_IN) return

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleSignInResult(task)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            //isn't used, but needed to catch potential error
            val account = completedTask.getResult(ApiException::class.java)
            startActivity()
        } catch (exception: ApiException) {
            loginFragment.showGoogleErrorMessage(exception.localizedMessage)
        }

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
                    loginFragment.showFacebookErrorMessage(exception.localizedMessage)
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