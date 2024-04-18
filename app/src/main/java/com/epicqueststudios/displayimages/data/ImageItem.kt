package com.epicqueststudios.displayimages.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItem(
    val id: Int,
    val attributes: ImageItemAttributes
): Parcelable