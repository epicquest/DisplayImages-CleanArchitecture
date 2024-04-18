package com.epicqueststudios.displayimages.data

import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imagesService: ImagesService
): ImageRepository {
    override suspend fun downloadImageList(): Resource<DownloadImagesResponse> {
        try {
            val response = imagesService.getImageList()
            if (response.isSuccessful) {
                val body = response.body()
                return Resource.Success(body!!)
            }
            return Resource.Error("Failed to fetch data.")
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.") //TODO MS
        }
    }

}