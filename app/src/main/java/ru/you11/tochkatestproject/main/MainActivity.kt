package ru.you11.tochkatestproject.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import ru.you11.tochkatestproject.R

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupNavigationDrawer()
        setupInitialFragment()
    }

    private fun setupInitialFragment() {

        val fragment = SearchFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, "SearchFragment")
            .commit()

        SearchPresenter(fragment)
    }

    override fun onBackPressed() {
        //TODO: Back press doesn't select correct nav drawer item
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val fragment: Fragment
        val fragmentTag: String

        drawer_layout.closeDrawer(GravityCompat.START)

        when (item.itemId) {
            R.id.nav_drawer_users -> {
                fragment = SearchFragment()
                SearchPresenter(fragment)
                fragmentTag = "SearchFragment"
            }

            R.id.nav_drawer_about_me -> {
                fragment = AboutMeFragment()
                AboutMePresenter(fragment)
                fragmentTag = "AboutMeFragment"
            }

            else -> {
                return false
            }
        }

        if (isCurrentFragmentExists(fragmentTag)) {
            return true
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragmentTag)
            .addToBackStack(null)
            .commit()

        return true
    }

    private fun isCurrentFragmentExists(fragmentName: String): Boolean {
        val currentFragment = supportFragmentManager.findFragmentByTag(fragmentName)
        if (currentFragment != null && currentFragment.isVisible) {
            return true
        }

        return false
    }

    private fun setupNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }
}
