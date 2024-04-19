package com.epicqueststudios.displayimages.data.network

import android.os.Parcelable
import com.epicqueststudios.displayimages.data.models.ImageItemData
import kotlinx.parcelize.Parcelize

@Parcelize
data class DownloadImagesResponse(
    val dataCollection: List<ImageItemData>
): Parcelable