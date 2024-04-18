package com.epicqueststudios.displayimages.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.epicqueststudios.displayimages.presentation.di.component.ActivityComponent
import com.epicqueststudios.displayimages.presentation.di.component.DaggerActivityComponent
import com.epicqueststudios.displayimages.presentation.di.module.ActivityContextModule
import com.epicqueststudios.displayimages.presentation.di.module.AppModule

abstract class BaseFragment<T: ViewDataBinding> : Fragment() {
    private lateinit var activityComponent: ActivityComponent
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .appModule(AppModule(requireActivity().application))
                .activityContextModule(ActivityContextModule(requireActivity()))
                .build()
        injectDependencies()
    }

    abstract fun injectDependencies()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflateFragment(inflater, container).root

    abstract fun inflateFragment(inflater: LayoutInflater, container: ViewGroup?): T

}
