package ru.you11.tochkatestproject.main

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.squareup.picasso.Picasso
import ru.you11.tochkatestproject.MainApp
import ru.you11.tochkatestproject.R
import ru.you11.tochkatestproject.model.GithubUser

class SearchFragment: Fragment(), MainContract.SearchContract.View {

    override lateinit var presenter: MainContract.SearchContract.Presenter

    private lateinit var searchBar: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var screenEmptyMessage: TextView
    private lateinit var pagesButtonsLayout: LinearLayout

    private val results = ArrayList<GithubUser>()
    private var latestQuery = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_search_users, container, false)
        with(root) {
            screenEmptyMessage = findViewById(R.id.search_screen_empty_message)
            recyclerView = findViewById(R.id.search_results_recycler_view)

            pagesButtonsLayout = findViewById(R.id.search_results_pages_buttons_layout)
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
                if (newText != null) {
                    latestQuery = newText
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    latestQuery = query
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
        recyclerView.visibility = RecyclerView.GONE
        pagesButtonsLayout.visibility = LinearLayout.GONE
        screenEmptyMessage.visibility = TextView.VISIBLE
    }

    override fun showGithubUsersPage(users: ArrayList<GithubUser>, page: Int, numberOfPages: Int) {
        results.clear()
        results.addAll(users)
        changePages(page, numberOfPages)
        recyclerView.adapter.notifyDataSetChanged()
        screenEmptyMessage.visibility = TextView.GONE
        recyclerView.visibility = RecyclerView.VISIBLE
        pagesButtonsLayout.visibility = LinearLayout.VISIBLE
    }

    private fun changePages(page: Int, numberOfPages: Int) {
        with(pagesButtonsLayout) {
            var counter = 0
            while (counter < pagesButtonsLayout.childCount) {
                with(pagesButtonsLayout.getChildAt(counter) as Button) {
                    isEnabled = true
                    setTypeface(null, Typeface.NORMAL)
                }
                counter++
            }

            val earliestButton = findViewById<Button>(R.id.search_results_pages_button_earliest)
            val previousButton = findViewById<Button>(R.id.search_results_pages_button_previous)
            val firstNumberedButton = findViewById<Button>(R.id.search_results_pages_button_number_1)
            val secondNumberedButton = findViewById<Button>(R.id.search_results_pages_button_number_2)
            val thirdNumberedButton = findViewById<Button>(R.id.search_results_pages_button_number_3)
            val fourthNumberedButton = findViewById<Button>(R.id.search_results_pages_button_number_4)
            val fifthNumberedButton = findViewById<Button>(R.id.search_results_pages_button_number_5)
            val nextButton = findViewById<Button>(R.id.search_results_pages_button_next)
            val lastButton = findViewById<Button>(R.id.search_results_pages_button_last)

            earliestButton.setOnClickListener {
                presenter.loadGithubUsers(latestQuery, 1)
            }

            previousButton.setOnClickListener {
                presenter.loadGithubUsers(latestQuery, page - 1)
            }

            nextButton.setOnClickListener {
                presenter.loadGithubUsers(latestQuery, page + 1)
            }

            lastButton.setOnClickListener {
                presenter.loadGithubUsers(latestQuery, numberOfPages)
            }


            val numberedButtonsArray = ArrayList<Button>()
            numberedButtonsArray.add(firstNumberedButton)
            numberedButtonsArray.add(secondNumberedButton)
            numberedButtonsArray.add(thirdNumberedButton)
            numberedButtonsArray.add(fourthNumberedButton)
            numberedButtonsArray.add(fifthNumberedButton)

            if (page == 1) {
                earliestButton.isEnabled = false
                previousButton.isEnabled = false

                if (numberOfPages == 1) {
                    nextButton.isEnabled = false
                    lastButton.isEnabled = false
                }
            }

            if (page == numberOfPages) {
                nextButton.isEnabled = false
                lastButton.isEnabled = false
            }

            setPageButtonsText(numberedButtonsArray, page, numberOfPages)
            highlightCurrentPageButton(numberedButtonsArray, page, numberOfPages)
            setOnClickListenersToPageButtons(numberedButtonsArray)
            setNumberedButtonsVisibility(numberedButtonsArray, numberOfPages, numberedButtonsArray.count())
        }
    }

    private fun setPageButtonsText(buttonsArray: ArrayList<Button>, page: Int, numberOfPages: Int) {
        if (page == 1 || page == 2 || page == 3) {
            buttonsArray.forEachIndexed { index, button ->
                button.text = (index + 1).toString()
            }
        } else if (page == numberOfPages || page == numberOfPages - 1 || page == numberOfPages - 2) {
            buttonsArray.forEachIndexed { index, button ->
                button.text = (numberOfPages - 4 + index).toString()
            }
        } else {
            buttonsArray.forEachIndexed { index, button ->
                button.text = (page - 2 + index).toString()
            }
        }
    }

    private fun highlightCurrentPageButton(buttonsArray: ArrayList<Button>, page: Int, numberOfPages: Int) {

        val button = if (page == 1 || page == 2) {
            buttonsArray[page - 1]
        } else if (page == numberOfPages || page == numberOfPages - 1) {
            buttonsArray[4 + page - numberOfPages]
        } else {
            buttonsArray[2]
        }

        button.setTypeface(null, Typeface.BOLD)
        button.isEnabled = false
    }

    //call after setting text
    //TODO: potentially can crash because it relies on text
    private fun setOnClickListenersToPageButtons(buttonsArray: ArrayList<Button>) {
        buttonsArray.forEach { button ->
            button.setOnClickListener {
                presenter.loadGithubUsers(latestQuery, button.text.toString().toInt())
            }
        }
    }

    private fun setNumberedButtonsVisibility(buttonsArray: ArrayList<Button>, numberOfPages: Int, counter: Int) {
        if (numberOfPages < counter) {
            buttonsArray[counter - 1].visibility = Button.GONE
            setNumberedButtonsVisibility(buttonsArray, numberOfPages, counter - 1)
        } else {
            buttonsArray.forEachIndexed { index, button ->
                if (index < counter) {
                    button.visibility = Button.VISIBLE
                }
            }
        }
    }

    override fun showLoadingGithubUsersError(error: Throwable) {
        Toast.makeText(activity, error.localizedMessage, Toast.LENGTH_SHORT).show()
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
        val name = layout.findViewById<TextView>(R.id.search_rw_username)
        val userId = layout.findViewById<TextView>(R.id.search_rw_user_id)
        val score = layout.findViewById<TextView>(R.id.search_rw_user_score)
        val avatar = layout.findViewById<ImageView>(R.id.search_rw_user_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_results_rw_layout, parent, false) as RelativeLayout

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = results[position]
        val resources = MainApp.applicationContext().resources
        holder.name.text = resources.getString(R.string.search_rw_username, user.login)
        holder.userId.text = resources.getString(R.string.search_rw_user_id, user.id)
        holder.score.text = resources.getString(R.string.search_rw_user_score, user.score)
        Picasso.get()
            .load(user.avatarUrl)
            .resize(200, 200)
            .centerCrop()
            .into(holder.avatar)
    }

    override fun getItemCount(): Int {
        return results.size
    }
}