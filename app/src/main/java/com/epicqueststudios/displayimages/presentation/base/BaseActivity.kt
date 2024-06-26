package com.epicqueststudios.displayimages.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.epicqueststudios.displayimages.presentation.di.component.ActivityComponent
import com.epicqueststudios.displayimages.presentation.di.component.AppComponent
import com.epicqueststudios.displayimages.presentation.di.component.DaggerActivityComponent
import com.epicqueststudios.displayimages.presentation.di.module.ActivityContextModule
import com.epicqueststudios.displayimages.presentation.di.module.AppModule

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
            .appModule(AppModule(application))
            .activityContextModule(ActivityContextModule(this))
            .build()
        injectDependencies()
    }
    abstract fun injectDependencies()
}

fun AppCompatActivity.getApplicationComponent(): AppComponent? {
    application?.let {
        return (it as? BaseApplication)?.appComponent
    }
    return null
}

fun Fragment.getApplicationComponent(): AppComponent? {
    activity?.application?.let {
        return (it as? BaseApplication)?.appComponent
    }
    return null
}