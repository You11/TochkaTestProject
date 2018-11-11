package ru.you11.tochkatestproject.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.SearchView
import ru.you11.tochkatestproject.R
import ru.you11.tochkatestproject.model.GithubUser

class SearchFragment: Fragment(), MainContract.SearchContract.View {

    override lateinit var presenter: MainContract.SearchContract.Presenter

    private lateinit var searchBar: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_search_users, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        searchBar = menu.findItem(R.id.menu_main_search).actionView as SearchView
        setupSearchBar()
    }

    override fun setupSearchBar() {
        with(searchBar) {
            queryHint = resources.getString(R.string.menu_search_hint)
            setOnQueryTextListener(createSearchQueryListener(this))
        }
    }

    private fun createSearchQueryListener(searchView: SearchView): SearchView.OnQueryTextListener {
        return object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    presenter.loadGithubUsers(query, 1)
                }
                searchView.clearFocus()
                return true
            }
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoGithubUsersScreen() {

    }

    override fun showGithubUsers(repositories: List<GithubUser>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingGithubUsersError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeRepositoriesFromUI() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}