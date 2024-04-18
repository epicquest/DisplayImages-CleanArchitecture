package com.epicqueststudios.displayimages

import android.app.Application
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.MainThread
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.epicqueststudios.displayimages.base.BaseViewModel
import com.epicqueststudios.displayimages.data.ImageItemData
import com.epicqueststudios.displayimages.data.Resource
import com.epicqueststudios.displayimages.domain.DownloadImagesUseCase
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    app: Application,
    private val coroutineScope: CoroutineScope,
    uiContext: CoroutineContext,
    private val savedStateHandle: SavedStateHandle,
    private val downloadImagesUseCase: DownloadImagesUseCase
) : BaseViewModel(app, uiContext), CoroutineScope {
    private val _images = MutableLiveData<Resource<List<ImageItemData>>>()
    val images: LiveData<Resource<List<ImageItemData>>> = _images
   // val savedData: LiveData<List<ImageItemData>> =
   //     savedStateHandle.getLiveData<List<ImageItemData>>(KEY_IMAGES)

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
                            _images.value =
                                Resource.Error(response.message ?: "Unknown error")  // TODO MS
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
    /*@Suppress("UNCHECKED_CAST")
    class Factory2(
        private val app: Application,
        private val downloadImagesUseCase: DownloadImagesUseCase
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(
                app,
                Dispatchers.Main,
                SavedStateViewModelFactory(app, this),
                downloadImagesUseCase
            ) as T
        }
    } */
    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val app: Application,
        private val coroutineScope: CoroutineScope,
        private val downloadImagesUseCase: DownloadImagesUseCase
    ) : ViewModelFactory<MainViewModel> {
        override fun create(handle: SavedStateHandle): MainViewModel {
            return MainViewModel(
                app,
                coroutineScope,
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
class GenericSavedStateViewModelFactory<out V : ViewModel>(
    private val viewModelFactory: ViewModelFactory<V>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return viewModelFactory.create(handle) as T
    }
}
interface ViewModelFactory<out V : ViewModel> {
    fun create(handle: SavedStateHandle): V
}

@MainThread
inline fun <reified VM : ViewModel> SavedStateRegistryOwner.withFactory(
    factory: ViewModelFactory<VM>,
    defaultArgs: Bundle? = null
) = GenericSavedStateViewModelFactory(factory, this, defaultArgs)