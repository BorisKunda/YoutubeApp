package com.boris.youtubeapp.network

import API_KEY
import BASE_URL
import com.boris.youtubeapp.model.SearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeController private constructor() : Callback<SearchResponse> {

    private val youtubeApi: YoutubeApi

    init {
        youtubeApi = getRetrofit(getOkHttpClient()).create(YoutubeApi::class.java)
    }

    companion object {

        val TAG = YoutubeController::class.java.simpleName

        fun getController(): YoutubeController {
            val instance: YoutubeController by lazy { YoutubeController() }
            return instance
        }

    }

    private fun getOkHttpClient(): OkHttpClient {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }.let {
            return OkHttpClient.Builder().addInterceptor(it).build()
        }
    }

    private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getFoo() {
        youtubeApi.getSearchResults("snippet", "android", "video", API_KEY).enqueue(this)
    }

    override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {

    }

    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {

    }

}


