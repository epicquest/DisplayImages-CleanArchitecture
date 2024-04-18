package com.epicqueststudios.displayimages.presentation.base

import android.app.Application
import com.epicqueststudios.displayimages.BuildConfig
import com.epicqueststudios.displayimages.presentation.di.component.AppComponent
import com.epicqueststudios.displayimages.presentation.di.component.DaggerAppComponent
import com.epicqueststudios.displayimages.presentation.di.module.AppModule
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