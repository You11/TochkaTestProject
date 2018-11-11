package ru.you11.tochkatestproject.main

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.you11.tochkatestproject.model.GithubService
import ru.you11.tochkatestproject.model.GithubUser
import ru.you11.tochkatestproject.model.GithubUserList

class SearchPresenter(private val searchView: SearchFragment): MainContract.SearchContract.Presenter {

    init {
        searchView.presenter = this
    }

    private var disposable = CompositeDisposable()

    override fun start() {
        searchView.showNoGithubUsersScreen()
    }

    override fun loadGithubUsers(query: String, page: Int) {
        val githubService = GithubService.create()
        disposable.add(githubService.usersSearch(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val userArrayList = getArrayListFromClass(result.users)
                val numberOfPages = getNumberOfPages(result.usersCount)
                searchView.showGithubUsersPage(userArrayList, page, numberOfPages)
            }, { error ->
                searchView.showLoadingGithubUsersError(error)
            }))

    }

    private fun getArrayListFromClass(users: List<GithubUser>): ArrayList<GithubUser> {
        val userArrayList = ArrayList<GithubUser>()
        users.forEach {
            userArrayList.add(it)
        }

        return userArrayList
    }

    private fun getNumberOfPages(numberOfUsers: Long): Int {
        val numberOfPages: Double = numberOfUsers / 30.0
        return Math.ceil(numberOfPages).toInt()
    }

    //in case of memory leaks
    //TODO: associated with lifecycle, should redone
    override fun disposeDisposables() {
        disposable.dispose()
    }
}
