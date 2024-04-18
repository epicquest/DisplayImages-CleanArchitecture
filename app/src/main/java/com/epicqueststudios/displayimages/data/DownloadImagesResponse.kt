package com.epicqueststudios.displayimages.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DownloadImagesResponse(
    val dataCollection: List<ImageItemData>
): Parcelable