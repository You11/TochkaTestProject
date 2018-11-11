package ru.you11.tochkatestproject.main

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList
import ru.you11.tochkatestproject.login.LoginActivity
import ru.you11.tochkatestproject.model.AppUser
import ru.you11.tochkatestproject.model.AuthMethod

class DrawerPresenter(private val activity: MainActivity): MainContract.DrawerContract.Presenter {

    init {
        activity.presenter = this
    }

    override fun start() {
        getUserInfo()
    }

    override fun getUserInfo() {
        if (AppUser.isLoggedIn()) {
            if (AppUser.getAuthMethod() == AuthMethod.VKontakte) {
                val params = VKParameters()
                params[VKApiConst.FIELDS] = "photo_200"
                val vkRequest = VKRequest("users.get", params)
                vkRequest.executeWithListener(object : VKRequest.VKRequestListener() {
                    override fun onComplete(response: VKResponse?) {
                        super.onComplete(response)
                        val user = response?.json?.getJSONArray("response")?.getJSONObject(0)
                        val firstName = user?.getString("first_name")
                        val lastName = user?.getString("last_name")
                        val photoUrl = user?.getString("photo_200")

                        if (photoUrl != null)
                            activity.displayUserInfo(AppUser("$firstName $lastName", photoUrl))
                        else
                            activity.displayUserInfo(AppUser("$firstName $lastName"))
                    }

                    override fun onError(error: VKError?) {
                        super.onError(error)
                        if (error != null) {
                            activity.displayVKUserInfoErrorMessage(error.errorMessage)
                        }
                    }
                })
            }
        }
    }

    override fun logOffUser() {
        when (AppUser.getAuthMethod()) {
            AuthMethod.VKontakte -> {
                VKSdk.logout()
            }

            AuthMethod.Google -> {

            }

            AuthMethod.Facebook -> {

            }

            else -> {

            }
        }

        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }
}
