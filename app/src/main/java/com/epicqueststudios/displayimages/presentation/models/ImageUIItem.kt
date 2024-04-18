package com.epicqueststudios.displayimages.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageUIItem(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
): Parcelable