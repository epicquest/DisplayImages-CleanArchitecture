package com.epicqueststudios.displayimages.di.component

import android.app.Activity
import android.app.Application
import android.content.Context
import com.epicqueststudios.displayimages.di.module.ActivityContextModule
import com.epicqueststudios.displayimages.di.module.AppModule
import com.epicqueststudios.displayimages.di.module.NetworkModule
import com.epicqueststudios.displayimages.di.module.VMFactoryModule
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