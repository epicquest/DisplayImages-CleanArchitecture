package com.epicqueststudios.displayimages.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.epicqueststudios.displayimages.MainViewModel
import com.epicqueststudios.displayimages.base.BaseActivity
import com.epicqueststudios.displayimages.base.getApplicationComponent
import com.epicqueststudios.displayimages.databinding.ActivityMainBinding
import com.epicqueststudios.displayimages.domain.withFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var mainViewModelFactory: MainViewModel.Factory
    private val mainViewModel: MainViewModel by viewModels {
        withFactory(mainViewModelFactory)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.downloadImages(false)
    }

    override fun injectDependencies() {
        getApplicationComponent()?.inject(this)
    }
}