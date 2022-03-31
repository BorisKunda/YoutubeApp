package com.boris.youtubeapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.boris.youtubeapp.R
import com.boris.youtubeapp.model.SearchResult
import com.boris.youtubeapp.utils.PLAYER_FRAGMENT_TAG
import com.boris.youtubeapp.utils.SEARCH_FRAGMENT_TAG
import com.boris.youtubeapp.viewmodel.YoutubeViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var searchScreenFragment: SearchScreenFragment
    private lateinit var fragmentManager: FragmentManager
    private val youtubeViewModel: YoutubeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        observeLiveData(youtubeViewModel)
    }

    private fun initUi() {
        fragmentManager = supportFragmentManager
        searchScreenFragment = SearchScreenFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_ll, searchScreenFragment, SEARCH_FRAGMENT_TAG).commit()
    }

    private fun observeLiveData(youtubeViewModel: YoutubeViewModel) {
        youtubeViewModel.clickedSearchResultSLD.observe(this)
        {
            openPlayerScreen(it)
        }
    }

    private fun openPlayerScreen(searchResult: SearchResult) {
        fragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_ll,
                PlayerScreenFragment.newInstance(youtubeViewModel.gson.toJson(searchResult)),
                PLAYER_FRAGMENT_TAG
            )
            .addToBackStack(PLAYER_FRAGMENT_TAG).commit()
    }

}