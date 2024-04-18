package com.epicqueststudios.displayimages.presentation.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module(includes = [AppModule::class, NetworkModule::class])

class VMFactoryModule {
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())
}