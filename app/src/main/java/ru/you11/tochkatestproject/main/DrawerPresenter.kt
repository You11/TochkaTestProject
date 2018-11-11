package ru.you11.tochkatestproject.main

import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList
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
                VKApi.users().get().executeWithListener(object : VKRequest.VKRequestListener() {
                    override fun onComplete(response: VKResponse?) {
                        super.onComplete(response)
                        val user = (response?.parsedModel as VKList<VKApiUser>)[0]
                        val username = user.first_name + " " + user.last_name
                        val userPhoto = user.photo_100

                        activity.displayUserInfo(AppUser(username, userPhoto))
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

    }
}
