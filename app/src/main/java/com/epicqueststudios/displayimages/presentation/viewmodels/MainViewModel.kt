package com.epicqueststudios.displayimages.presentation.viewmodels

import android.app.Application
import android.os.Parcelable
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.epicqueststudios.displayimages.presentation.base.BaseViewModel
import com.epicqueststudios.displayimages.domain.Resource
import com.epicqueststudios.displayimages.domain.DownloadImagesUseCase
import com.epicqueststudios.displayimages.presentation.factories.ViewModelFactory
import com.epicqueststudios.displayimages.presentation.models.ImageUIItem
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
    private val _images = MutableLiveData<Resource<List<ImageUIItem>>>()
    val images: LiveData<Resource<List<ImageUIItem>>> = _images

        fun downloadImages(forced: Boolean) {
            viewModelScope.launch {
                try {
                    _images.value = Resource.Loading()
                    val savedList = retrieveData() as List<ImageUIItem>? ?: listOf()
                    if (!forced && savedList.isNotEmpty()) {
                        _images.value = Resource.Success(savedList)
                    } else {
                        val response = downloadImagesUseCase.downloadImages()
                        if (response is Resource.Success) {
                            saveData(response.data!!)
                        }
                        _images.value = response
                    }
                } catch (e: Exception) {
                    _images.value = Resource.Error(e.message!!)
                    Timber.e(e)
                }
            }

        }
    fun saveData(value: List<Parcelable>) {
        savedStateHandle[KEY_IMAGES] = value
    }
    @VisibleForTesting
    fun retrieveData(): List<Parcelable>? {
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
