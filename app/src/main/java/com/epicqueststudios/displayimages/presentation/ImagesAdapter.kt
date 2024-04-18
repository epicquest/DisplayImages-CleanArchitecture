package com.epicqueststudios.displayimages.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.epicqueststudios.displayimages.R
import com.epicqueststudios.displayimages.data.ImageItemData
import com.epicqueststudios.displayimages.databinding.ItemImageBinding


class ImagesAdapter: RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    private lateinit var binding: ItemImageBinding
    private var imagesList: MutableList<ImageItemData> = mutableListOf()

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

            val drawablePlaceholder = CircularProgressDrawable(binding.root.context)
            drawablePlaceholder.setColorSchemeColors(
                R.color.purple_500,
                R.color.purple_700,
                R.color.teal_200
            )
            drawablePlaceholder.setCenterRadius(30f)
            drawablePlaceholder.setStrokeWidth(5f)
            // set all other properties as you would see fit and start it
            // set all other properties as you would see fit and start it
            drawablePlaceholder.start()
            Glide
                .with(binding.itemImagePicture)
                .load(data.item.attributes.imageInfo.imageUrl)
                .fitCenter()
                .placeholder(drawablePlaceholder)
                .into(binding.itemImagePicture)
        }
    }
}