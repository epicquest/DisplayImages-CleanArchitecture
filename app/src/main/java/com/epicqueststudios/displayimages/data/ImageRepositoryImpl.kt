package com.epicqueststudios.displayimages.data

import com.epicqueststudios.displayimages.data.repositiories.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imagesService: ImagesService
): ImageRepository {
    override suspend fun downloadImageList(): Resource<DownloadImagesResponse> {
        try {
            val response = imagesService.getImageList()
            if (response.isSuccessful) {
                return Resource.Success(response.body()!!)
            }
            return Resource.Error(response.errorBody().toString())
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
    }
}