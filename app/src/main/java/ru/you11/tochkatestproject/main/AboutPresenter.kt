package ru.you11.tochkatestproject.main

class AboutPresenter(aboutFragmentView: AboutFragment): MainContract.AboutContract.Presenter {

    init {
        aboutFragmentView.presenter = this
    }

    override fun start() {

    }
}