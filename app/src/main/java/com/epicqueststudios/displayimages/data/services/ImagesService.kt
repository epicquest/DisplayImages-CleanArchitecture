package com.epicqueststudios.displayimages.data.services

import com.epicqueststudios.displayimages.data.network.DownloadImagesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ImagesService {
    @GET("mondly_android_code_task_json")
    suspend fun getImageList(): Response<DownloadImagesResponse>
}