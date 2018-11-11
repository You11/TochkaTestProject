package ru.you11.tochkatestproject.main

import ru.you11.tochkatestproject.BasePresenter
import ru.you11.tochkatestproject.BaseView
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

            fun showGithubUsers(users: ArrayList<GithubUser>)

            fun showLoadingGithubUsersError()

            fun removeRepositoriesFromUI()
        }

        interface Presenter: BasePresenter {

            fun loadGithubUsers(query: String, page: Int)

            fun disposeDisposables()
        }
    }

    interface DrawerContract {

        interface View: BaseView<Presenter> {

            fun displayUsername()

            fun displayPhoto()
        }

        interface Presenter: BasePresenter {

            fun logOffUser()
        }
    }
}