package com.epicqueststudios.displayimages.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.epicqueststudios.displayimages.MainViewModel
import com.epicqueststudios.displayimages.base.BaseFragment
import com.epicqueststudios.displayimages.base.getApplicationComponent
import com.epicqueststudios.displayimages.data.Resource
import com.epicqueststudios.displayimages.databinding.FragmentImagesBinding

class FirstFragment : BaseFragment<FragmentImagesBinding>() {
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
                    imagesAdapter.setImages(it.data!!) //TODO MS
                    Toast.makeText(context, "downloaded data", Toast.LENGTH_SHORT).show()
                }
            }
        }
       /* mainViewModel.savedData.observe(viewLifecycleOwner) {
                binding.progress.gone()
                imagesAdapter.setImages(it)
                Toast.makeText(context, "savedData", Toast.LENGTH_SHORT).show()
        }*/
    }

    private fun showLoading() {
        binding.progress.visible()
        binding.tvError.gone()
    }

    private fun showError(message: String?) {
        binding.progress.gone()
        binding.tvError.text = message
        binding.tvError.visible()
    }
}