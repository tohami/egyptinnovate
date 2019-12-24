package com.tohami.egyptinnovate.app

import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.tohami.egyptinnovate.app.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class InnovateApplication : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        setupPicasso()
        Finit.init(this)
    }

    private fun setupPicasso() {
        Picasso.Builder(this).apply {
            downloader(OkHttp3Downloader(this@InnovateApplication, Integer.MAX_VALUE.toLong()))
            loggingEnabled(true)
            indicatorsEnabled(true)
        }.run {
            try {
                Picasso.setSingletonInstance(build())
            } catch (ex: IllegalStateException) {
                ex.printStackTrace()
            }
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}
