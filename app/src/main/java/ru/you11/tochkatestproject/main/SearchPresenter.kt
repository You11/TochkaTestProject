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
                val userArrayList = getArrayListFromClass(result)
                searchView.showGithubUsers(userArrayList)
            }, { error ->
                Log.d("retrofitTesting", error.localizedMessage)
            }))

    }

    private fun getArrayListFromClass(result: GithubUserList): ArrayList<GithubUser> {
        val userArrayList = ArrayList<GithubUser>()
        result.users.forEach {
            userArrayList.add(it)
        }

        return userArrayList
    }

    //in case of memory leaks
    //TODO: associated with lifecycle, should redone
    override fun disposeDisposables() {
        disposable.dispose()
    }
}
