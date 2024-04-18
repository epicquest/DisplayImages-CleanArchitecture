package com.epicqueststudios.displayimages.di.module

import com.epicqueststudios.displayimages.data.ImageRepository
import com.epicqueststudios.displayimages.data.ImageRepositoryImpl
import com.epicqueststudios.displayimages.data.ImagesService
import com.epicqueststudios.displayimages.domain.DownloadImagesUseCase
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [AppModule::class]
)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = (HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object  {
        const val BASE_URL = "https://europe-west1-mondly-workflows.cloudfunctions.net/"
    }

    @Singleton
    @Provides
    fun provideImagesService(retrofit: Retrofit) = retrofit.create(ImagesService::class.java)
    @Singleton
    @Provides
    fun provideImageRepository(service: ImagesService): ImageRepository = ImageRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideDownloadImagesUseCase(repository: ImageRepository):DownloadImagesUseCase = DownloadImagesUseCase(repository)


}
