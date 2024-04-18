package com.epicqueststudios.displayimages.presentation.base

open class BaseEvent<out T>(private val content: T) {
    var handled = false
        private set

    fun getContentIfNotHandled(): T? {
        synchronized(this) {
            return if (handled) {
                null
            } else {
                handled = true
                content
            }
        }
    }

    fun peekContent(): T = content
}