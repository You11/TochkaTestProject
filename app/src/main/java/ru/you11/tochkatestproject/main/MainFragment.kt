package ru.you11.tochkatestproject.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.SearchView
import ru.you11.tochkatestproject.R

class MainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_search_users, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        (menu.findItem(R.id.menu_main_search).actionView as SearchView).apply {
            setupSearchView()
        }
    }

    private fun SearchView.setupSearchView() {
        queryHint = resources.getString(R.string.menu_search_hint)
        setOnQueryTextListener(createSearchQueryListener(this))
    }

    private fun createSearchQueryListener(searchView: SearchView): SearchView.OnQueryTextListener {
        return object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchGithubUsers(query)
                searchView.clearFocus()
                return true
            }
        }
    }

    private fun searchGithubUsers(query: String?) {
        if (query == null) return
    }
}