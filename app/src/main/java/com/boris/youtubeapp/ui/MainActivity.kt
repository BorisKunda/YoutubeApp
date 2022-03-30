package com.boris.youtubeapp.ui

import SEARCH_FRAGMENT_TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.boris.youtubeapp.R
import com.boris.youtubeapp.YoutubeRepository


class MainActivity : AppCompatActivity() {

    private lateinit var searchScreenFragment: SearchScreenFragment
    private lateinit var playerScreenFragment: PlayerScreenFragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_ll, searchScreenFragment, SEARCH_FRAGMENT_TAG).commit()
        YoutubeRepository.getRepository(this.application)

    }

    private fun initUi() {
        searchScreenFragment = SearchScreenFragment()
        playerScreenFragment = PlayerScreenFragment()
        fragmentManager = supportFragmentManager
    }
}