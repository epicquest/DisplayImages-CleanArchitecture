package com.epicqueststudios.displayimages.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItemAttributes(
    val name: String,
    val description: String,
    val imageInfo: ImageItemInfo
): Parcelable