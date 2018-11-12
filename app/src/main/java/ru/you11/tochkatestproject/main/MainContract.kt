package ru.you11.tochkatestproject.main

import com.vk.sdk.api.VKError
import ru.you11.tochkatestproject.BasePresenter
import ru.you11.tochkatestproject.BaseView
import ru.you11.tochkatestproject.model.AppUser
import ru.you11.tochkatestproject.model.GithubUser

interface MainContract {

    interface AboutMeContract {

        interface View: BaseView<Presenter> {

            fun showAboutMePage()
        }

        interface Presenter: BasePresenter
    }

    interface SearchContract {

        interface View: BaseView<Presenter> {

            fun setLoadingIndicator(active: Boolean)

            fun showNoGithubUsersScreen()

            fun showGithubUsersPage(users: ArrayList<GithubUser>, page: Int, numberOfPages: Int)

            fun showLoadingGithubUsersError(error: Throwable)

            fun removeRepositoriesFromUI()
        }

        interface Presenter: BasePresenter {

            fun loadWithDelay(query: String)

            fun loadGithubUsers(query: String, page: Int)

            fun disposeDisposables()
        }
    }

    interface MainActivityContract {

        interface View: BaseView<Presenter> {

            fun displayUserInfo(user: AppUser)

            fun displayVKUserInfoErrorMessage(errorMessage: String)
        }

        interface Presenter: BasePresenter {

            fun setupGoogleApiClient()

            fun getUserInfo()

            fun logOffUser()
        }
    }
}