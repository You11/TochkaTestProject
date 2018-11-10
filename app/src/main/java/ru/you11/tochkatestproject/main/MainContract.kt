package ru.you11.tochkatestproject.main

import ru.you11.tochkatestproject.BasePresenter
import ru.you11.tochkatestproject.BaseView
import ru.you11.tochkatestproject.model.Repository

interface MainContract {

    interface View: BaseView<Presenter> {

        //TODO: Move nav. drawer methods somewhere else probably
        fun displayUsername()

        fun displayPhoto()


        fun setLoadingIndicator(active: Boolean)

        fun showNoRepositoresScreen()

        fun showRepositories(repositories: List<Repository>)

        fun showLoadingRepositoriesError()

        fun removeRepositoriesFromUI()

        fun showAboutMePage()
    }

    interface Presenter: BasePresenter {

        fun loadRepositories()

        fun logOffUser()

    }
}