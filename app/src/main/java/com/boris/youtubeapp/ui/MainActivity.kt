package com.boris.youtubeapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.boris.youtubeapp.R
import com.boris.youtubeapp.utils.SEARCH_FRAGMENT_TAG
import com.boris.youtubeapp.utils.VIDEO_ID_KEY
import com.boris.youtubeapp.viewmodel.SearchScreenViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var searchScreenFragment: SearchScreenFragment
    private lateinit var fragmentManager: FragmentManager
    private val searchScreenViewModel: SearchScreenViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        observeLiveData(searchScreenViewModel)
    }

    private fun initUi() {
        fragmentManager = supportFragmentManager
        searchScreenFragment = SearchScreenFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_ll, searchScreenFragment, SEARCH_FRAGMENT_TAG).commit()
    }

    private fun observeLiveData(searchScreenViewModel: SearchScreenViewModel) {
        searchScreenViewModel.clickedSearchResultVideoIdSLD.observe(this)
        {
            openPlayerScreen(it)
        }
    }

    private fun openPlayerScreen(videoId: String) {
        Intent(this@MainActivity, PlayerActivity::class.java).apply {
            putExtra(VIDEO_ID_KEY, videoId)
        }.let {
            this@MainActivity.startActivity(it)
        }
    }

}