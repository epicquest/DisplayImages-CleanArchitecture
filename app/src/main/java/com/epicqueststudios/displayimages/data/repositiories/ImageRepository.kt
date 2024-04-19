package com.epicqueststudios.displayimages.data.repositiories

import androidx.lifecycle.SavedStateHandle
import com.epicqueststudios.displayimages.data.network.DownloadImagesResponse
import com.epicqueststudios.displayimages.domain.Resource

interface ImageRepository {
    suspend fun downloadImageList(savedStateHandle: SavedStateHandle, forced: Boolean): Resource<DownloadImagesResponse>
}