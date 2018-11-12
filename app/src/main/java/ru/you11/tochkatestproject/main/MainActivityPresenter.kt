package ru.you11.tochkatestproject.main

import android.content.Intent
import com.facebook.Profile
import com.facebook.ProfileTracker
import com.facebook.login.LoginManager
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import ru.you11.tochkatestproject.login.LoginActivity
import ru.you11.tochkatestproject.model.AppUser
import ru.you11.tochkatestproject.model.AuthMethod

class MainActivityPresenter(private val activity: MainActivity): MainContract.DrawerContract.Presenter {

    init {
        activity.presenter = this
    }

    override fun start() {
        getUserInfo()
    }

    override fun getUserInfo() {
        if (!AppUser.isLoggedIn()) return

        when (AppUser.getAuthMethod()) {
            AuthMethod.VKontakte -> {
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

            AuthMethod.Google -> {

            }

            AuthMethod.Facebook -> {
                if (Profile.getCurrentProfile() == null) {
                    val profileTracker = object : ProfileTracker() {
                        override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile) {
                            this.stopTracking()
                            Profile.setCurrentProfile(currentProfile)
                            getFBInfoFromProfile(currentProfile)
                        }
                    }

                    profileTracker.startTracking()
                } else {
                    getFBInfoFromProfile(Profile.getCurrentProfile())
                }
            }

            else -> {

            }
        }
    }

    //TODO: rename
    private fun getFBInfoFromProfile(profile: Profile) {
        val firstName = profile.firstName
        val lastName = profile.lastName
        val photoUrl = profile.getProfilePictureUri(200, 200)
        activity.displayUserInfo(AppUser("$firstName $lastName", photoUrl.toString()))
    }

    override fun logOffUser() {
        when (AppUser.getAuthMethod()) {
            AuthMethod.VKontakte -> {
                VKSdk.logout()
            }

            AuthMethod.Google -> {

            }

            AuthMethod.Facebook -> {
                LoginManager.getInstance().logOut()
            }

            else -> {

            }
        }

        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }
}
