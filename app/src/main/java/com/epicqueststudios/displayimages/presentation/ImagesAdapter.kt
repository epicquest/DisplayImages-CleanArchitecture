package com.epicqueststudios.displayimages.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.epicqueststudios.displayimages.data.ImageItemData
import com.epicqueststudios.displayimages.databinding.ItemImageBinding

@SuppressLint("NotifyDataSetChanged")
class ImagesAdapter: RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    private lateinit var binding: ItemImageBinding
    private var imagesList: MutableList<ImageItemData> = mutableListOf()

    fun clearImages() {
        imagesList.clear()
        notifyDataSetChanged()
    }

    fun setImages(images: List<ImageItemData>) {
        imagesList.clear()
        imagesList.addAll(images)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val imageItem = imagesList[position]
        holder.bind(imageItem)
    }

    override fun getItemCount(): Int = imagesList.size

    inner class ImagesViewHolder(
        private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ImageItemData) {
            binding.itemImageTitle.text = data.item.attributes.name
            binding.itemImageDescription.text = data.item.attributes.description

            Glide
                .with(binding.itemImagePicture)
                .load(data.item.attributes.imageInfo.imageUrl)
                .fitCenter()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.itemImagePicture)
        }
    }
}