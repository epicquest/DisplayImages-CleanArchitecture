package com.epicqueststudios.displayimages.domain

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

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