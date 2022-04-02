package com.boris.youtubeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.boris.youtubeapp.model.SearchResult
import com.boris.youtubeapp.repository.YoutubeRepository
import com.boris.youtubeapp.utils.SingleLiveEvent

class SearchScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val youtubeRepository: YoutubeRepository = YoutubeRepository.getRepository(application)
    var searchResultsListLD: LiveData<List<SearchResult>> = youtubeRepository.searchResultsListMLD
    val clickedSearchResultVideoIdSLD: SingleLiveEvent<String> = SingleLiveEvent()
    val searchRequestFailedSld: SingleLiveEvent<Void> = youtubeRepository.searchRequestFailedSld
    val requestTimeoutSld: SingleLiveEvent<Void> = SingleLiveEvent()

    fun searchVideosByKeyword(keyword: String) {
        youtubeRepository.searchVideosByKeyword(keyword)
    }

}