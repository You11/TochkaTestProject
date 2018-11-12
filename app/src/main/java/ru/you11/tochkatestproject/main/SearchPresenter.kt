package ru.you11.tochkatestproject.main

import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.you11.tochkatestproject.model.GithubService
import ru.you11.tochkatestproject.model.GithubUser
import java.util.*
import java.util.concurrent.TimeUnit

class SearchPresenter(private val searchFragment: SearchFragment): MainContract.SearchContract.Presenter {

    init {
        searchFragment.presenter = this
    }

    private val DELAY_TIME_MS: Long = 600

    private var compDisposable = CompositeDisposable()
    private lateinit var onInputDisposable: Disposable

    override fun start() {
        searchFragment.showStartingScreen()
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
        updateAndroidSecurityProvider()
        compDisposable.add(githubService.usersSearch(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val userArrayList = getArrayListFromClass(result.users)
                val numberOfPages = getNumberOfPages(result.usersCount)
                if (numberOfPages == 0) {
                    searchFragment.showNoGithubUsersFoundScreen()
                } else {
                    searchFragment.showGithubUsersPage(userArrayList, page, numberOfPages)
                }
            }, { error ->
                searchFragment.showLoadingGithubUsersError(error)
            }))
    }

    //TODO: Still will show error pre-install
    private fun updateAndroidSecurityProvider() {
        try {
            ProviderInstaller.installIfNeeded(searchFragment.activity)
        } catch (exception: GooglePlayServicesRepairableException) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            Log.e("SecurityException", exception.localizedMessage)
        } catch (exception: GooglePlayServicesNotAvailableException) {
            Log.e("SecurityException", "Google Play Services not available.")
        }
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
