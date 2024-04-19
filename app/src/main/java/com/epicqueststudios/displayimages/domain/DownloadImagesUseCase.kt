package com.epicqueststudios.displayimages.domain

import androidx.lifecycle.SavedStateHandle
import com.epicqueststudios.displayimages.data.repositiories.ImageRepository
import com.epicqueststudios.displayimages.presentation.models.ImageUIItem
import javax.inject.Inject

class DownloadImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend fun downloadImages(savedStateHandle: SavedStateHandle, forced: Boolean): Resource<List<ImageUIItem>> {
        val res = repository.downloadImageList(savedStateHandle, forced)
        return if (res is Resource.Success) {
            val t = res.data?.dataCollection?.map {
                ImageUIItem(it.item.id, it.item.attributes.name,it.item.attributes.description,it.item.attributes.imageInfo.imageUrl)
            }
            if (t != null) Resource.Success(t) else Resource.Error(res.message?: "")
        } else Resource.Error(res.message?: "")
    }
}