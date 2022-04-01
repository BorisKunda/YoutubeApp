package com.boris.youtubeapp.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.boris.youtubeapp.model.SearchResponse
import com.boris.youtubeapp.model.SearchResult
import com.boris.youtubeapp.network.YoutubeApiResponseListener
import com.boris.youtubeapp.network.YoutubeController

class YoutubeRepository private constructor(private val application: Application) :
    YoutubeApiResponseListener {

    val searchResultsListMLD: MutableLiveData<List<SearchResult>> = MutableLiveData()

    companion object {

        val TAG = YoutubeRepository::class.java.simpleName

        fun getRepository(application: Application): YoutubeRepository {
            val instance: YoutubeRepository by lazy { YoutubeRepository(application) }
            return instance
        }

    }

    fun searchVideosByKeyword(keyword: String) {
        YoutubeController.getController(this).searchVideosByKeyword(keyword = keyword)
    }

    override fun onSuccess(response: SearchResponse) {
        searchResultsListMLD.value = response.items.toList()
    }

    override fun onError(message: String) {
        Log.e(TAG, message)
        Toast.makeText(application, message, Toast.LENGTH_SHORT).show()
    }

}