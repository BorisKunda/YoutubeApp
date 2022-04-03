package com.boris.youtubeapp.ui

import android.os.Bundle
import android.util.Log
import com.boris.youtubeapp.R
import com.boris.youtubeapp.utils.API_KEY
import com.boris.youtubeapp.utils.VIDEO_ID_KEY
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class PlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {


    private var videoId: String? = null
    private lateinit var playerView: YouTubePlayerView
    private var player: YouTubePlayer? = null
    private val TAG: String = PlayerActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        playerView = findViewById(R.id.player_ypv)
        playerView.initialize(API_KEY, this)
        videoId = intent.getStringExtra(VIDEO_ID_KEY)
    }

    override fun onInitializationSuccess(
        youtubeProvider: YouTubePlayer.Provider?,
        youtubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        player = youtubePlayer
        if (!videoId.isNullOrEmpty()) {
            player?.loadVideo(videoId)
        }

    }

    override fun onInitializationFailure(
        youtubeProvider: YouTubePlayer.Provider?,
        error: YouTubeInitializationResult?
    ) {
        (error ?: YouTubeInitializationResult.UNKNOWN_ERROR).let {
            Log.e(TAG, "An error occurred while initialising the Youtube player - ${it.name}")
        }
    }

}
