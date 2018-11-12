package ru.you11.tochkatestproject.model

import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.Profile
import com.vk.sdk.VKSdk

data class AppUser(val username: String,
                   val photoUrl: String? = "https://www.brandeps.com/icon-download/U/User-02.svg") {

    companion object {
        fun getAuthMethod(): AuthMethod? {
            if (VKSdk.isLoggedIn()) {
                return AuthMethod.VKontakte
            }

            if (isLoggedInFacebook()) {
                return AuthMethod.Facebook
            }

            return null
        }

        fun isLoggedIn(): Boolean {
            if (VKSdk.isLoggedIn()) {
                return true
            }

            if (isLoggedInFacebook()) {
                return true
            }

            return false
        }

        private fun isLoggedInFacebook(): Boolean {
            return (AccessToken.getCurrentAccessToken() != null)
        }
    }
}