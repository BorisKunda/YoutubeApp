package com.boris.youtubeapp.network

import com.boris.youtubeapp.model.SearchResponse
import com.boris.youtubeapp.utils.API_KEY
import com.boris.youtubeapp.utils.BASE_URL
import com.boris.youtubeapp.utils.NULL_RESPONSE_ERROR_MESSAGE
import com.boris.youtubeapp.utils.STANDARD_ERROR_MESSAGE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeController private constructor(youtubeApiResponseListener: YoutubeApiResponseListener) :
    Callback<SearchResponse> {

    private val youtubeApi: YoutubeApi
    private val responseListener: YoutubeApiResponseListener

    init {
        youtubeApi = getRetrofit(getOkHttpClient()).create(YoutubeApi::class.java)
        responseListener = youtubeApiResponseListener
    }

    companion object {

        val TAG = YoutubeController::class.java.simpleName

        fun getController(youtubeApiResponseListener: YoutubeApiResponseListener): YoutubeController {
            val instance: YoutubeController by lazy { YoutubeController(youtubeApiResponseListener) }
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

    fun searchVideosByKeyword(
        part: String = "snippet",
        maxResults: String = "10",
        keyword: String,
        type: String = "video",
        apiKey: String = API_KEY
    ) {
        youtubeApi.getSearchResults(part, maxResults, keyword, type, apiKey).enqueue(this)
    }

    override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {

        val searchResponse: SearchResponse? = response.body()

        if (searchResponse != null) {
            responseListener.onSuccess(searchResponse)
        } else {
            responseListener.onError(NULL_RESPONSE_ERROR_MESSAGE)
        }

    }

    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {

        val message: String? = t.message

        if (message != null) {
            responseListener.onError(message)
        } else {
            responseListener.onError(STANDARD_ERROR_MESSAGE)
        }
    }

}


