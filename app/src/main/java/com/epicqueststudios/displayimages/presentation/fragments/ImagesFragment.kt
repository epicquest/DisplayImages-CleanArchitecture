package com.epicqueststudios.displayimages.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.epicqueststudios.displayimages.presentation.viewmodels.MainViewModel
import com.epicqueststudios.displayimages.presentation.base.BaseFragment
import com.epicqueststudios.displayimages.presentation.base.getApplicationComponent
import com.epicqueststudios.displayimages.domain.Resource
import com.epicqueststudios.displayimages.databinding.FragmentImagesBinding
import com.epicqueststudios.displayimages.presentation.adapters.ImagesAdapter
import com.epicqueststudios.displayimages.presentation.views.gone
import com.epicqueststudios.displayimages.presentation.views.visible

class ImagesFragment : BaseFragment<FragmentImagesBinding>() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var imagesAdapter: ImagesAdapter
    override fun injectDependencies() {
        getApplicationComponent()?.inject(this)
    }

    override fun inflateFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentImagesBinding {
        binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvImages.adapter

        imagesAdapter = ImagesAdapter()

        binding.rvImages.apply {
            adapter = imagesAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        binding.swipeRefresh.setOnRefreshListener {
            imagesAdapter.clearImages()
            mainViewModel.downloadImages(true)
            binding.swipeRefresh.isRefreshing = false
        }
        binding.btnRetry.setOnClickListener {
            mainViewModel.downloadImages(true)
        }
        initObservers()
    }

    private fun initObservers() {
        mainViewModel.images.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {
                    showError(it.message)
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    binding.progress.gone()
                    imagesAdapter.setImages(it.data ?: listOf())
                }
            }
        }
    }

    override fun onDestroyView() {
        mainViewModel.images.removeObservers(this)
        super.onDestroyView()
    }
    private fun showLoading() {
        binding.progress.visible()
        binding.tvError.gone()
        binding.btnRetry.gone()
    }

    private fun showError(message: String?) {
        binding.progress.gone()
        binding.tvError.text = message
        binding.tvError.visible()
        binding.btnRetry.visible()
    }
}