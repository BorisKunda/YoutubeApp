package com.boris.youtubeapp.model

import com.google.gson.annotations.SerializedName

data class ThumbnailsContainer(
    @SerializedName("default") val smallThumbnail: Thumbnail,
    @SerializedName("medium") val mediumThumbnail: Thumbnail,
    @SerializedName("high") val highThumbnail: Thumbnail
)