package com.boris.youtubeapp.model

import com.google.gson.annotations.SerializedName

data class Snippet(
    val title: String,
    val description: String,
    @SerializedName("thumbnails") val thumbnailsContainer: ThumbnailsContainer
)

