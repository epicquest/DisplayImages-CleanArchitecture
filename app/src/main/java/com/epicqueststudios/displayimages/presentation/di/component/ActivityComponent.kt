package com.epicqueststudios.displayimages.presentation.di.component

import android.app.Activity
import android.app.Application
import android.content.Context
import com.epicqueststudios.displayimages.presentation.di.module.ActivityContextModule
import com.epicqueststudios.displayimages.presentation.di.module.AppModule
import com.epicqueststudios.displayimages.presentation.di.module.NetworkModule
import com.epicqueststudios.displayimages.presentation.di.module.VMFactoryModule
import com.epicqueststudios.displayimages.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ActivityContextModule::class, VMFactoryModule::class, NetworkModule::class])
interface ActivityComponent {

    fun provideContext(): Context
    fun provideActivity(): Activity
    fun provideApplication(): Application
    fun inject(activity: MainActivity)
}