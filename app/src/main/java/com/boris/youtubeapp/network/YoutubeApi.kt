package com.boris.youtubeapp.network

import com.boris.youtubeapp.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YoutubeApi {
    @GET("search")
    fun getSearchResults(
        @Query("part") part: String,
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): Call<SearchResponse>
}
