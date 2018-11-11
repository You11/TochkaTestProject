package ru.you11.tochkatestproject

import android.app.Application
import android.content.Context
import com.vk.sdk.VKSdk

class MainApp: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(applicationContext)
    }

}