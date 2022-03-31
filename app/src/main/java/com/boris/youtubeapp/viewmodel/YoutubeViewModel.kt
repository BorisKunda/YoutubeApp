package com.boris.youtubeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.boris.youtubeapp.model.SearchResult
import com.boris.youtubeapp.repository.YoutubeRepository

class YoutubeViewModel(application: Application) : AndroidViewModel(application) {

    private val youtubeRepository: YoutubeRepository = YoutubeRepository.getRepository(application)
    var searchResultsListLD: LiveData<List<SearchResult>> = youtubeRepository.searchResultsListMLD

    fun searchVideosByKeyword(keyword: String) {
        youtubeRepository.searchVideosByKeyword(keyword)
    }

}