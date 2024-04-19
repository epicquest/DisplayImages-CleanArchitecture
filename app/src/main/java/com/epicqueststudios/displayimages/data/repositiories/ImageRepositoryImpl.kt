package com.epicqueststudios.displayimages.data.repositiories

import androidx.lifecycle.SavedStateHandle
import com.epicqueststudios.displayimages.data.network.DownloadImagesResponse
import com.epicqueststudios.displayimages.data.services.ImagesService
import com.epicqueststudios.displayimages.domain.Resource
import com.epicqueststudios.displayimages.presentation.viewmodels.MainViewModel
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imagesService: ImagesService
): ImageRepository {
    override suspend fun downloadImageList(savedStateHandle: SavedStateHandle, forced: Boolean): Resource<DownloadImagesResponse> {
        try {
            if (!forced) {
                val savedList = retrieveData(savedStateHandle)
                if (savedList?.dataCollection?.isNotEmpty() == true) {
                    return Resource.Success(savedList)
                }
            }
            val response = imagesService.getImageList()
            if (response.isSuccessful) {
                val data = response.body()!!
                saveData(savedStateHandle, data)
                return Resource.Success(data)
            }
            return Resource.Error(response.errorBody().toString())
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "")
        }
    }

    fun saveData(savedStateHandle: SavedStateHandle, value: DownloadImagesResponse) {
        savedStateHandle[MainViewModel.KEY_IMAGES] = value
    }

    fun retrieveData(savedStateHandle: SavedStateHandle): DownloadImagesResponse? {
        return savedStateHandle[MainViewModel.KEY_IMAGES]
    }
}