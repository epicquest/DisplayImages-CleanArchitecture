package com.epicqueststudios.displayimages.base

import android.app.Application
import com.epicqueststudios.displayimages.BuildConfig
import com.epicqueststudios.displayimages.di.component.AppComponent
import com.epicqueststudios.displayimages.di.component.DaggerAppComponent
import com.epicqueststudios.displayimages.di.module.AppModule
import timber.log.Timber

abstract class BaseApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


}