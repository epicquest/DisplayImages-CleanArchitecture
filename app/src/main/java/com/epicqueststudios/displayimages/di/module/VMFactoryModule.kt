package com.epicqueststudios.displayimages.di.module

import android.app.Activity
import android.app.Application
import androidx.savedstate.SavedStateRegistryOwner
import com.epicqueststudios.displayimages.MainViewModel
import com.epicqueststudios.displayimages.domain.DownloadImagesUseCase
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
    /*@Singleton
    @Provides
    fun provideMainViewModelFactory(
        app: Application,
        coroutineScope: CoroutineScope,
        downloadImagesUseCase: DownloadImagesUseCase
    ) = MainViewModel.Factory(app, coroutineScope, downloadImagesUseCase)
*/
}