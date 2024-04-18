package com.epicqueststudios.displayimages.domain

import com.epicqueststudios.displayimages.data.DownloadImagesResponse
import com.epicqueststudios.displayimages.data.ImageRepository
import com.epicqueststudios.displayimages.data.Resource
import javax.inject.Inject

class DownloadImagesUseCase @Inject constructor(
    private val repository: ImageRepository) {
    suspend fun downloadImages(): Resource<DownloadImagesResponse> {
        return repository.downloadImageList()
    }
}