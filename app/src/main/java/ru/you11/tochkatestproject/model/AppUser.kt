package ru.you11.tochkatestproject.model

import com.vk.sdk.VKSdk

data class AppUser(val username: String,
                   val photoUrl: String? = "https://www.brandeps.com/icon-download/U/User-02.svg") {

    companion object {
        fun getAuthMethod(): AuthMethod? {
            if (VKSdk.isLoggedIn()) {
                return AuthMethod.VKontakte
            }

            return null
        }

        fun isLoggedIn(): Boolean {
            if (VKSdk.isLoggedIn()) {
                return true
            }

            return false
        }
    }
}