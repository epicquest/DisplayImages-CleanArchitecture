package com.epicqueststudios.displayimages.di.component

import android.app.Application
import android.content.Context
import com.epicqueststudios.displayimages.presentation.ImagesFragment
import com.epicqueststudios.displayimages.di.module.AppModule
import com.epicqueststudios.displayimages.di.module.VMFactoryModule
import com.epicqueststudios.displayimages.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, VMFactoryModule::class])
interface AppComponent {
    fun provideContext(): Context
    fun provideApplication(): Application
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: ImagesFragment)
}