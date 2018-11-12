package ru.you11.tochkatestproject.model

import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.Profile
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.vk.sdk.VKSdk
import ru.you11.tochkatestproject.MainApp

data class AppUser(val username: String,
                   val photoUrl: String? = null) {

    companion object {
        fun getAuthMethod(): AuthMethod? {
            if (isLoggedInVK()) {
                return AuthMethod.VKontakte
            }

            if (isLoggedInFacebook()) {
                return AuthMethod.Facebook
            }

            if (isLoggedInGoogle()) {
                return AuthMethod.Google
            }

            return null
        }

        fun isLoggedIn(): Boolean {
            if (isLoggedInVK()) {
                return true
            }

            if (isLoggedInFacebook()) {
                return true
            }

            if (isLoggedInGoogle()) {
                return true
            }

            return false
        }

        private fun isLoggedInVK(): Boolean {
            return VKSdk.isLoggedIn()
        }

        private fun isLoggedInFacebook(): Boolean {
            return (AccessToken.getCurrentAccessToken() != null)
        }

        private fun isLoggedInGoogle(): Boolean {
            return (GoogleSignIn.getLastSignedInAccount(MainApp.applicationContext()) != null)
        }
    }
}