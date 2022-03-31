package com.boris.youtubeapp.network

import com.boris.youtubeapp.model.SearchResponse

interface YoutubeApiResponseListener {
    fun onSuccess(response: SearchResponse)
    fun onError(message: String)
}