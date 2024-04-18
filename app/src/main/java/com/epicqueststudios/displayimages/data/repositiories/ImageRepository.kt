package com.epicqueststudios.displayimages.data.repositiories

import com.epicqueststudios.displayimages.data.DownloadImagesResponse
import com.epicqueststudios.displayimages.domain.Resource

interface ImageRepository {
    suspend fun downloadImageList(): Resource<DownloadImagesResponse>
}