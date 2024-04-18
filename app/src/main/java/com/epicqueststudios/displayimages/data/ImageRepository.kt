package com.epicqueststudios.displayimages.data

interface ImageRepository {
    suspend fun downloadImageList(): Resource<DownloadImagesResponse>
}