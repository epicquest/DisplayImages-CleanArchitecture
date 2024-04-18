package com.epicqueststudios.displayimages.data

import retrofit2.Response
import retrofit2.http.GET

interface ImagesService {
    @GET("mondly_android_code_task_json")
    suspend fun getImageList(): Response<DownloadImagesResponse>
}