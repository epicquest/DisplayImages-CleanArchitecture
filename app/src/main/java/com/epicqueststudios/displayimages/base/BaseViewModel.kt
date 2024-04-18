package com.epicqueststudios.displayimages.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(val app: Application, private val uiContext: CoroutineContext): AndroidViewModel(app),
    CoroutineScope {

    var ioCoroutineContext = Dispatchers.IO
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = uiContext + job

}