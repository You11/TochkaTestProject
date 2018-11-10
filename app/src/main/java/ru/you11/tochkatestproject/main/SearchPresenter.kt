package ru.you11.tochkatestproject.main

import android.widget.SearchView

class SearchPresenter(private val searchView: SearchFragment): MainContract.SearchContract.Presenter {

    init {
        searchView.presenter = this
    }

    override fun start() {
        searchView.showNoGithubUsersScreen()
    }

    override fun loadGithubUsers() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
