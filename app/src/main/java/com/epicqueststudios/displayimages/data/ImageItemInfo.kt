package com.epicqueststudios.displayimages.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItemInfo (
    val imageUrl: String
): Parcelable