package ru.you11.tochkatestproject.main

import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.you11.tochkatestproject.model.GithubService
import ru.you11.tochkatestproject.model.GithubUser
import java.util.*
import java.util.concurrent.TimeUnit

class SearchPresenter(private val searchView: SearchFragment): MainContract.SearchContract.Presenter {

    init {
        searchView.presenter = this
    }

    private val DELAY_TIME_MS: Long = 600

    private var compDisposable = CompositeDisposable()
    private lateinit var onInputDisposable: Disposable

    override fun start() {
        searchView.showStartingScreen()
    }

    override fun loadWithDelay(query: String) {
        if (this::onInputDisposable.isInitialized)
            onInputDisposable.dispose()

        onInputDisposable = Completable.timer(DELAY_TIME_MS, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("loadedWithDelay", query)
                loadGithubUsers(query, 1)
            }
        compDisposable.add(onInputDisposable)
    }

    override fun loadGithubUsers(query: String, page: Int) {
        val githubService = GithubService.create()
        compDisposable.add(githubService.usersSearch(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val userArrayList = getArrayListFromClass(result.users)
                val numberOfPages = getNumberOfPages(result.usersCount)
                if (numberOfPages == 0) {
                    searchView.showNoGithubUsersFoundScreen()
                } else {
                    searchView.showGithubUsersPage(userArrayList, page, numberOfPages)
                }
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
        compDisposable.dispose()
    }
}
