package com.epicqueststudios.displayimages

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.*
import com.epicqueststudios.displayimages.base.BaseViewModel
import com.epicqueststudios.displayimages.data.ImageItemData
import com.epicqueststudios.displayimages.data.Resource
import com.epicqueststudios.displayimages.domain.DownloadImagesUseCase
import com.epicqueststudios.displayimages.domain.ViewModelFactory
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    app: Application,
    uiContext: CoroutineContext,
    private val savedStateHandle: SavedStateHandle,
    private val downloadImagesUseCase: DownloadImagesUseCase
) : BaseViewModel(app, uiContext), CoroutineScope {
    private val _images = MutableLiveData<Resource<List<ImageItemData>>>()
    val images: LiveData<Resource<List<ImageItemData>>> = _images

        fun downloadImages(forced: Boolean) {
            viewModelScope.launch {
                try {
                    _images.value = Resource.Loading()
                    val savedList = retrieveData() as List<ImageItemData>?
                    if (!forced && savedList?.isNotEmpty() == true) {
                        _images.value = Resource.Success(savedList)
                    } else {
                        val response = downloadImagesUseCase.downloadImages()
                        if (response is Resource.Success) {
                            saveData(response.data!!.dataCollection)
                            _images.value = Resource.Success(response.data.dataCollection)
                        } else if (response is Resource.Error) {
                            _images.value = Resource.Error(response.message ?: "Unknown error")
                        }
                    }
                } catch (e: Exception) {
                    _images.value = Resource.Error(e.message!!)
                    Timber.e(e)
                }
            }

        }
    private fun saveData(value: List<Parcelable>) {
        savedStateHandle[KEY_IMAGES] = value
    }

    private fun retrieveData(): List<Parcelable>? {
        return savedStateHandle[KEY_IMAGES]
    }

    class Factory @Inject constructor(
        private val app: Application,
        private val downloadImagesUseCase: DownloadImagesUseCase
    ) : ViewModelFactory<MainViewModel> {
        override fun create(handle: SavedStateHandle): MainViewModel {
            return MainViewModel(
                app,
                Dispatchers.Main,
                handle,
                downloadImagesUseCase
            )
        }
    }

    companion object {
        const val KEY_IMAGES = "key_images"
    }
}
