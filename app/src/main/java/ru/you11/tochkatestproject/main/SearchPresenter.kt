package ru.you11.tochkatestproject.main

import android.util.Log
import android.widget.SearchView
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.you11.tochkatestproject.model.GithubService

class SearchPresenter(private val searchView: SearchFragment): MainContract.SearchContract.Presenter {

    init {
        searchView.presenter = this
    }

    override fun start() {
        searchView.showNoGithubUsersScreen()
    }

    override fun loadGithubUsers(query: String, page: Int) {
        val githubService = GithubService.create()
        val disposable = githubService.usersSearch(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                result.users.forEach {
                    Log.d("retrofitTesting", it.login)
                }
            }, { error ->
                Log.d("retrofitTesting", error.localizedMessage)
            })

    }
}
