package com.epicqueststudios.displayimages.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.epicqueststudios.displayimages.di.component.ActivityComponent
import com.epicqueststudios.displayimages.di.component.DaggerActivityComponent
import com.epicqueststudios.displayimages.di.module.ActivityContextModule
import com.epicqueststudios.displayimages.di.module.AppModule

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

    companion object {

        private val TAG = BaseFragment::class.java.simpleName
    }
}
