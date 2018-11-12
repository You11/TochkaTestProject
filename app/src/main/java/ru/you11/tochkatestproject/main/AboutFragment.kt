package ru.you11.tochkatestproject.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.you11.tochkatestproject.R

class AboutFragment: Fragment() {

    lateinit var presenter: AboutPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
}