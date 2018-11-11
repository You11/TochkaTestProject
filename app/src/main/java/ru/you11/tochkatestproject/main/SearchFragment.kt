package ru.you11.tochkatestproject.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.TextView
import ru.you11.tochkatestproject.R
import ru.you11.tochkatestproject.model.GithubUser

class SearchFragment: Fragment(), MainContract.SearchContract.View {

    override lateinit var presenter: MainContract.SearchContract.Presenter

    private lateinit var searchBar: SearchView
    private lateinit var recyclerView: RecyclerView

    private val results = ArrayList<GithubUser>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_search_users, container, false)
        with(root) {
            recyclerView = findViewById(R.id.search_results_recycler_view)
            setupRecyclerView()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
        presenter.start()
    }

    private fun setupRecyclerView() {
        val rvManager = LinearLayoutManager(activity)
        val rvAdapter = SearchRecyclerViewAdapter(results)
        with(recyclerView) {
            layoutManager = rvManager
            addItemDecoration(DividerItemDecoration(context, rvManager.orientation))
            adapter = rvAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        searchBar = menu.findItem(R.id.menu_main_search).actionView as SearchView
        setupSearchBar()
    }

    private fun setupSearchBar() {
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

    override fun showGithubUsers(users: ArrayList<GithubUser>) {
        results.clear()
        results.addAll(users)
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun showLoadingGithubUsersError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeRepositoriesFromUI() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        presenter.disposeDisposables()
        super.onPause()
    }
}

class SearchRecyclerViewAdapter(private val results: ArrayList<GithubUser>): RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val layout: RelativeLayout): RecyclerView.ViewHolder(layout) {
        val name = layout.findViewById<TextView>(R.id.search_rw_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_results_rw_layout, parent, false) as RelativeLayout

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = results[position]
        holder.name.text = user.login
    }

    override fun getItemCount(): Int {
        return results.size
    }
}