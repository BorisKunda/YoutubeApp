package com.boris.youtubeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.boris.youtubeapp.model.SearchResult
import com.boris.youtubeapp.repository.YoutubeRepository
import com.boris.youtubeapp.utils.SingleLiveEvent
import com.google.gson.Gson

class YoutubeViewModel(application: Application) : AndroidViewModel(application) {

    private val youtubeRepository: YoutubeRepository = YoutubeRepository.getRepository(application)
    var searchResultsListLD: LiveData<List<SearchResult>> = youtubeRepository.searchResultsListMLD
    val clickedSearchResultSLD: SingleLiveEvent<SearchResult> = SingleLiveEvent()
    val gson: Gson = Gson()

    fun searchVideosByKeyword(keyword: String) {
        youtubeRepository.searchVideosByKeyword(keyword)
    }

}