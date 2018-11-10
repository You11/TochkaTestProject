package ru.you11.tochkatestproject.main

class AboutMePresenter(aboutMeView: AboutMeFragment): MainContract.AboutMeContract.Presenter {

    init {
        aboutMeView.presenter = this
    }

    override fun start() {

    }
}